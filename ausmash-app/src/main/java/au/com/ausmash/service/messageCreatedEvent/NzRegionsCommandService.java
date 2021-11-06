package au.com.ausmash.service.messageCreatedEvent;

import java.util.Arrays;
import java.util.List;

import au.com.ausmash.model.NzRegion;
import au.com.ausmash.model.Region;
import au.com.ausmash.service.CommandService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NzRegionsCommandService implements CommandService {
    private static final Logger LOG = LoggerFactory.getLogger(NzRegionsCommandService.class);
    private static final String HEADER = "=====NZ PR Regions=====\n";
    public static final String COMMAND_NAME = "nzregions";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (!messageComponents.isEmpty()) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }

        final String message = Arrays.stream(NzRegion.values())
            .map(NzRegion::toString)
            .map(StringUtils::trim)
            .reduce(HEADER, (partialString, element) -> partialString.concat("\n").concat(element));
        return messageChannel.flatMap(channel -> channel.createMessage(message));
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        return messageChannel.flatMap(channel ->
            channel.createMessage("Returns a list of all NZ PR regions and their PR region codes")
        );
    }
}