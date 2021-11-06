package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;
import au.com.ausmash.model.Region;
import au.com.ausmash.rest.messageCreatedEvent.ChannelsController;
import au.com.ausmash.rest.messageCreatedEvent.RegionsController;
import au.com.ausmash.service.CommandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RegionsCommandService implements CommandService {
    @Autowired
    private RegionsController regionsController;

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsCommandService.class);
    private static final String HEADER = "=====REGIONS=====\n";
    public static final String COMMAND_NAME = "regions";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (!messageComponents.isEmpty()) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }
        final List<Region> regions = regionsController.listAll();
        final String message = regions.stream()
            .map(Region::toString)
            .map(StringUtils::trim)
            .reduce(HEADER, (partialString, element) -> partialString.concat("\n").concat(element));
        return messageChannel.flatMap(channel -> channel.createMessage(message));
    }
}