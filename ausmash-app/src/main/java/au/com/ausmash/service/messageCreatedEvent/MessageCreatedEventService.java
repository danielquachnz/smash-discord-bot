package au.com.ausmash.service.messageCreatedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.service.CommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreatedEventService {

    @Autowired
    private MessageCreatedEventMapperService messageCreatedEventMapperService;

    static final String UNRECOGNISED_COMMAND = "Sorry, that command was not recognised";
    private static final String COMMAND_PREFIX = "!";

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

        final String command = messageComponents.get(0);
        messageComponents.remove(0);

        final Optional<CommandService> toMonoMessageService
            = messageCreatedEventMapperService.getCommandService(command);

        if (toMonoMessageService.isPresent()) {
            final List<String> params = ImmutableList.copyOf(messageComponents);
            return toMonoMessageService.get().processMessage(messageChannel, params);
        }

        return messageChannel.flatMap(channel -> channel.createMessage(UNRECOGNISED_COMMAND));
    }
}