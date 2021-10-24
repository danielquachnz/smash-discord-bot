package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Elo;
import au.com.ausmash.model.Player;
import au.com.ausmash.model.Region;
import au.com.ausmash.rest.messageCreatedEvent.PlayersController;
import au.com.ausmash.rest.messageCreatedEvent.RegionsController;
import au.com.ausmash.service.CommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EloCommandService  implements CommandService {
    @Autowired
    private PlayersController playersController;

    private static final Logger LOG = LoggerFactory.getLogger(EloCommandService.class);
    static final String COMMAND_NAME = "elo";

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
        final List<Elo> elos = playersController.getEloForPlayer(name, region);

        if (elos.isEmpty()){
            return Mono.empty();
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final String header = String.format("===ELO for %s (%s)===\n",
            elos.get(0).getPlayer().getName(), elos.get(0).getPlayer().getRegionShort());
        stringBuilder.append(header);
        for (final Elo elo : elos) {
            stringBuilder.append(elo.toString()).append("\n");
        }
        return messageChannel.flatMap(channel -> channel.createMessage((stringBuilder.toString())));

    }
}