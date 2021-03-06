package au.com.ausmash.service.messageCreatedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.com.ausmash.model.Game;
import au.com.ausmash.model.Player;
import au.com.ausmash.model.Region;
import au.com.ausmash.model.WinRate;
import au.com.ausmash.rest.messageCreatedEvent.GamesController;
import au.com.ausmash.rest.messageCreatedEvent.PlayersController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.ParameterUtil;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WinratesCommandService implements CommandService {
    @Autowired
    private PlayersController playersController;
    @Autowired
    private GamesController gamesController;

    private static final Logger LOG = LoggerFactory.getLogger(WinratesCommandService.class);
    private static final String HEADER = "===Win rates for %s (%s)===\n";
    static final String COMMAND_NAME = "winrates";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (messageComponents.size() < 2) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }

        final String regionString = messageComponents.get(messageComponents.size() - 1);

        final StringBuilder builder = new StringBuilder(messageComponents.get(0));
        for (int i = 1; i < messageComponents.size() - 1; i++) {
            builder.append(" ");
            builder.append(messageComponents.get(i));
        }

        final String name = builder.toString();
        final Player player = playersController.find(name, regionString);

        final StringBuilder stringBuilder = new StringBuilder();
        final String header = String.format(HEADER, player.getName(), player.getRegionShort().name());
        stringBuilder.append(header);

        Arrays.stream(Game.GameType.values()).forEachOrdered(gameType -> {
            final List<WinRate> winRates = playersController.listWinratesForPlayerAndGame(player.getId(), gameType.getId());
            totalWinRateDetails(winRates).ifPresent(winRateDetails ->
                stringBuilder.append(
                    String.format("%s - %s\n", gameType.name(), winRateDetails)
                )
            );
        });

        return messageChannel.flatMap(channel -> channel.createMessage((stringBuilder.toString())));
    }

    private Optional<String> totalWinRateDetails(List<WinRate> winRates) {
        if (winRates.isEmpty()) {
            return Optional.empty();
        }

        int totalWins = winRates.stream()
            .map(WinRate::getWins)
            .reduce(0, Integer::sum);

        int totalLosses =
            winRates.stream()
                .map(WinRate::getLosses)
                .reduce(0, Integer::sum);


        if (totalWins == 0 && totalLosses == 0) {
            return Optional.empty();
        }

        double totalGames = totalWins + totalLosses;
        double winRatePercentage =  totalWins * 100.0 / totalGames;

        return Optional.of(String.format(
            "%s sets won, %s sets lost, set win rate = %.2f%%",
            totalWins,
            totalLosses,
            winRatePercentage
        ));
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s%s [%s] [%s]\n\n",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            ParameterUtil.PLAYER_NAME,
            ParameterUtil.REGION_SHORT
        ));

        stringBuilder.append("Returns the win rates for each game for the given player\n");

        stringBuilder.append(String.format(
            "e.g. %s%s %s %s",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            "Wontonz",
            Region.RegionType.NZ.name()
        ));

        return messageChannel.flatMap(channel -> channel.createMessage(stringBuilder.toString()));
    }
}