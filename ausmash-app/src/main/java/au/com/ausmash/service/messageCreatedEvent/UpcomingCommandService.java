package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Region;
import au.com.ausmash.model.Tourney;
import au.com.ausmash.model.validator.RegionValidator;
import au.com.ausmash.rest.messageCreatedEvent.TourneysController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.ParameterUtil;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class UpcomingCommandService implements CommandService {

    @Autowired
    private TourneysController tourneysController;

    private static final Logger LOG = LoggerFactory.getLogger(UpcomingCommandService.class);
    private static final String HEADER_ALL = "=====Upcoming Tournaments=====\n";
    private static final String HEADER_REGION = "=====Upcoming Tournaments For %s=====\n";
    static final String COMMAND_NAME = "upcoming";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {

        if (messageComponents.isEmpty()) {
            final String message = tourneysController.listUpcoming().stream()
                .map(Tourney::toString)
                .map(StringUtils::trim)
                .reduce(HEADER_ALL, (partialString, element) -> partialString.concat("\n\n").concat(element));
            return messageChannel.flatMap(channel -> channel.createMessage(message));
        }

        if (messageComponents.size() == 1) {
            final String region = messageComponents.get(0);
            RegionValidator.validateShortName(region);
            final String header = String.format(HEADER_REGION, region.toUpperCase());
            final String message = tourneysController.listUpcoming().stream()
                .filter(t -> StringUtils.equalsIgnoreCase(t.getRegionShort(), region))
                .map(Tourney::toString)
                .map(StringUtils::trim)
                .reduce(header, (partialString, element) -> partialString.concat("\n\n").concat(element));
            return messageChannel.flatMap(channel -> channel.createMessage(message));
        }

        LOG.info(messageComponents.stream()
            .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
        return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(
            "%s%s returns a list of all upcoming tournaments\n\n",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME
        ));

        stringBuilder.append(String.format(
            "%s%s [%s] returns a list of all upcoming tournaments for a region\n\n",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            ParameterUtil.REGION_SHORT
        ));

        stringBuilder.append(String.format(
            "e.g. %s%s %s",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            Region.RegionType.NZ.name()
        ));

        return messageChannel.flatMap(channel -> channel.createMessage("Returns a list of all upcoming tournaments"));
    }
}