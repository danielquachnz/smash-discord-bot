package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Player;
import au.com.ausmash.model.Region;
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
public class PlayerCommandService implements CommandService {
    @Autowired
    private PlayersController playersController;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerCommandService.class);
    static final String COMMAND_NAME = "player";
    private static final String HEADER = "=====%s (%s)=====\n";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (messageComponents.size() < 2) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return messageChannel.flatMap(channel -> channel.createMessage(MessageCreatedEventService.UNRECOGNISED_COMMAND));
        }

        final String region = messageComponents.get(messageComponents.size() - 1);

        final StringBuilder builder = new StringBuilder(messageComponents.get(0));
        for (int i = 1; i < messageComponents.size() - 1; i++) {
            builder.append(" ");
            builder.append(messageComponents.get(i));
        }

        final String name = builder.toString();
        final Player player = playersController.find(name, region);
        LOG.info(String.format("player name = %s", name));
        final String header = String.format(HEADER, player.getName(), player.getRegionShort().name());
        return messageChannel.flatMap(channel -> channel.createMessage(header.concat(player.toString())));
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

        stringBuilder.append("Returns the player information of the given player\n");

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