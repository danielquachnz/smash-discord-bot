package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.service.CommandService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class PingCommandService implements CommandService {
    private static final Logger LOG = LoggerFactory.getLogger(PingCommandService.class);
    static final String COMMAND_NAME = "ping";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (!messageComponents.isEmpty()) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }
        return messageChannel.flatMap(channel -> channel.createMessage("pong!"));
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        return messageChannel.flatMap(channel -> channel.createMessage("try it"));
    }
}