package au.com.ausmash.service.messageCreatedEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.config.AusmashConfig;
import au.com.ausmash.model.Game;
import au.com.ausmash.model.NzRegion;
import au.com.ausmash.model.Ranking;
import au.com.ausmash.model.Region;
import au.com.ausmash.model.validator.GameValidator;
import au.com.ausmash.model.validator.PrRegionValidator;
import au.com.ausmash.rest.messageCreatedEvent.RankingsController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.ParameterUtil;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PrCommandService implements CommandService {
    @Autowired
    private RankingsController rankingsController;

    private static final Logger LOG = LoggerFactory.getLogger(PrCommandService.class);
    static final String COMMAND_NAME = "pr";
    private static final String HEADER = "=====%s - %s (%s)=====\n";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (messageComponents.size() != 2) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }

        final String prRegion = messageComponents.get(0).toUpperCase();
        final String game = messageComponents.get(1);

        GameValidator.validateShortName(game);
        PrRegionValidator.validateShortName(prRegion);

        final boolean isNzRegion = EnumUtils.isValidEnum(NzRegion.class, prRegion);
        final String region = isNzRegion ? Region.RegionType.NZ.name() : prRegion;

        List<Ranking> rankings = rankingsController.listByRegionAndGame(region, game);

        if (isNzRegion) {
            final NzRegion nzRegion = NzRegion.valueOf(prRegion);
            rankings = rankings.stream()
                .filter(r -> StringUtils.containsIgnoreCase(r.getSequenceName(), nzRegion.getDescription()))
                .collect(Collectors.toList());
        }

        final Optional<Ranking> oRanking = rankings.stream()
            .max(Comparator.comparing(Ranking::getId));

        if (!oRanking.isPresent()) {
            return messageChannel.flatMap(channel -> channel.createMessage(
                String.format("No PR found for region [%s] and game [%s]", prRegion, game)
            ));
        }

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(
            HEADER,
            oRanking.get().getSequenceName(),
            oRanking.get().getVersion(),
            oRanking.get().getGameShort()
        ));

        rankings.stream().max(Comparator.comparing(Ranking::getId))
            .ifPresent(stringBuilder::append);

        return messageChannel.flatMap(channel -> channel.createMessage(stringBuilder.toString()));
    }


    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s%s [%s/%s] [%s]\n\n",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            ParameterUtil.REGION_SHORT,
            ParameterUtil.NZ_PR_REGION,
            ParameterUtil.GAME_SHORT
        ));

        stringBuilder.append("Returns the latest power ranking for region and game\n");

        stringBuilder.append(String.format(
            "e.g. %s%s %s %s",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            Region.RegionType.NZ.name(),
            Game.GameType.SSBU.name()
        ));

        return messageChannel.flatMap(channel -> channel.createMessage(stringBuilder.toString()));
    }
}