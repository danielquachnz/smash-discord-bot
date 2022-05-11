package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;
import au.com.ausmash.rest.messageCreatedEvent.ChannelsController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.EmbeddedMessageHelper;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class ChannelsCommandService implements CommandService {

    @Autowired
    private ChannelsController channelsController;

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsCommandService.class);
    private static final String HEADER = "=====Channels=====\n";
    static final String COMMAND_NAME = "channels";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (!messageComponents.isEmpty()) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return EmbeddedMessageHelper.createMessage(messageChannel, MessageCreatedEventService.UNRECOGNISED_COMMAND);
            //return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }
        final List<Channel> channels = channelsController.listAll();
        final String message = channels.stream()
            .map(Channel::toString)
            .map(StringUtils::trim)
            .reduce(HEADER, (partialString, element) -> partialString.concat("\n").concat(element));
        //return messageChannel.flatMap(channel -> channel.createMessage(message));
        return EmbeddedMessageHelper.createMessage(messageChannel, message);
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        //return messageChannel.flatMap(channel -> channel.createMessage("Returns a list of all channels"));
        return EmbeddedMessageHelper.createMessage(messageChannel,"Returns a list of all channels");
    }
}