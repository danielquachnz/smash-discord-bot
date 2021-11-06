package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.config.AusmashConfig;
import au.com.ausmash.model.Match;
import au.com.ausmash.model.Player;
import au.com.ausmash.model.PlayerShort;
import au.com.ausmash.model.Vod;
import au.com.ausmash.model.validator.RegionValidator;
import au.com.ausmash.rest.messageCreatedEvent.PlayersController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.UrlUtil;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VodsCommandService implements CommandService {
    @Autowired
    private PlayersController playersController;
    @Autowired
    private AusmashConfig ausmashConfig;

    private static final Logger LOG = LoggerFactory.getLogger(VodsCommandService.class);
    static final String COMMAND_NAME = "vods";

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
        final List<Vod> vods = playersController.getVodsForPlayer(name, region);

        if (vods.isEmpty()){
            return messageChannel.flatMap(channel -> channel.createMessage(String.format("No VODs found for %s, region: %s", name, region)));
        }

        final PlayerShort player = getPlayer(vods.get(0).getMatch(), name);
        final StringBuilder stringBuilder = new StringBuilder();
        final String header = String.format("===Latest VODs for %s (%s)===\n", name, region);
        stringBuilder.append(header);
        for (final Vod vod : vods) {
            stringBuilder.append(vod.toString()).append("\n");
        }
        final String vodsURL = UrlUtil.createUrl(
            ausmashConfig.getSiteUrl(), "players", Integer.toString(player.getId()), player.getName(), "videos");
        stringBuilder.append(String.format("For more VODs visit %s", vodsURL));
        LOG.info(stringBuilder.toString());
        return messageChannel.flatMap(channel -> channel.createMessage(stringBuilder.toString()));

    }

    private PlayerShort getPlayer(Match match, String name) {
        if (StringUtils.equalsIgnoreCase(match.getWinner().getName(), name)) {
            return match.getWinner();
        }

        if (StringUtils.equalsIgnoreCase(match.getLoser().getName(), name)) {
            return match.getLoser();
        }

        if (StringUtils.equalsIgnoreCase(match.getTeamWinner1().getName(), name)) {
            return match.getTeamWinner1();
        }

        if (StringUtils.equalsIgnoreCase(match.getTeamWinner2().getName(), name)) {
            return match.getTeamWinner2();
        }

        if (StringUtils.equalsIgnoreCase(match.getTeamLoser1().getName(), name)) {
            return match.getTeamLoser1();
        }

        return match.getTeamLoser2();
    }
}