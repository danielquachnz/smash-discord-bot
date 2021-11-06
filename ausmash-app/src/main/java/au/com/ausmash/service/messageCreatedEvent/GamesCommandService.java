package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Game;
import au.com.ausmash.rest.messageCreatedEvent.GamesController;
import au.com.ausmash.service.CommandService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GamesCommandService implements CommandService {
    @Autowired
    private GamesController gamesController;

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsCommandService.class);
    private static final String HEADER = "=====GAMES=====";
    public static final String COMMAND_NAME = "games";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (!messageComponents.isEmpty()) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }
        final List<Game> games = gamesController.listAll();
        final String message = games.stream()
            .map(Game::toString)
            .map(StringUtils::trim)
            .reduce(HEADER, (partialString, element) -> partialString.concat("\n").concat(element));
        return messageChannel.flatMap(channel -> channel.createMessage(message));
    }

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        return messageChannel.flatMap(channel ->
            channel.createMessage("Returns a list of all games and their game codes")
        );
    }
}