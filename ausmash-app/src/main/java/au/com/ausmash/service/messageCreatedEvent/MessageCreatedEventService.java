package au.com.ausmash.service.messageCreatedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.rest.exception.HttpException;
import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.CommandService;
import com.google.common.collect.ImmutableList;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreatedEventService {

    @Autowired
    private MessageCreatedEventMapperService messageCreatedEventMapperService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageCreatedEventService.class);
    static final String UNRECOGNISED_COMMAND = "Sorry, that command was not recognised";
    public static final String COMMAND_PREFIX = "!!";

    public Mono<Message> process(MessageCreateEvent event){
        final Mono<MessageChannel> messageChannel = event.getMessage().getChannel();
        final String messageContent = event.getMessage().getContent();

        if (!StringUtils.startsWith(messageContent, COMMAND_PREFIX)
            || messageContent.split("\\s+")[0].length() <= COMMAND_PREFIX.length()) {
            return Mono.empty();
        }

        final List<String> messageComponents
            = Arrays.stream(StringUtils.removeFirst(messageContent, COMMAND_PREFIX).split("\\s+"))
                .map(StringUtils::trim)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        final String command = messageComponents.get(0).toLowerCase();
        messageComponents.remove(0);

        final Optional<CommandService> toMonoMessageService
            = messageCreatedEventMapperService.getCommandService(command);

        if (toMonoMessageService.isPresent()) {
            final List<String> params = ImmutableList.copyOf(messageComponents);
            try {
                return toMonoMessageService.get().processMessage(messageChannel, params);
            } catch (final HttpException e) {
                LOG.info(String.format("Encountered HttpException of type %s: %s", e.getClass().getSimpleName(), e.getMessage()));
                return messageChannel.flatMap(channel -> channel.createMessage(e.getMessage()));
            } catch (final ValidationException e) {
                LOG.info(String.format("Encountered ValidationException %s", e.getMessage()));
                return messageChannel.flatMap(channel -> channel.createMessage(e.getMessage()));
            }
        }

        return messageChannel.flatMap(channel -> channel.createMessage(UNRECOGNISED_COMMAND));
    }
}