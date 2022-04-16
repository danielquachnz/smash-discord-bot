package au.com.ausmash.service.messageCreatedEvent;

import au.com.ausmash.config.AusmashConfig;
import au.com.ausmash.model.Match;
import au.com.ausmash.model.PlayerShort;
import au.com.ausmash.model.Region;
import au.com.ausmash.model.Vod;
import au.com.ausmash.rest.messageCreatedEvent.PlayersController;
import au.com.ausmash.service.CommandService;
import au.com.ausmash.util.EmbeddedMessageHelper;
import au.com.ausmash.util.ParameterUtil;
import au.com.ausmash.util.UrlUtil;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class VodsCommandService implements CommandService {
    @Autowired
    private PlayersController playersController;
    @Autowired
    private AusmashConfig ausmashConfig;

    private static final Logger LOG = LoggerFactory.getLogger(VodsCommandService.class);
    static final String COMMAND_NAME = "vods";
    private static final String HEADER = "=====Latest VODs for %s (%s)=====\n";

    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (messageComponents.size() < 2) {
            LOG.info(messageComponents.stream()
                .reduce(COMMAND_NAME.concat(" messageComponents:"), (partialString, element) -> partialString.concat(" ").concat(element)));
            return EmbeddedMessageHelper.createErrorMessage(messageChannel, MessageCreatedEventService.UNRECOGNISED_COMMAND);
        }

        final String region = messageComponents.get(messageComponents.size() - 1);

        final StringBuilder builder = new StringBuilder(messageComponents.get(0));
        for (int i = 1; i < messageComponents.size() - 1; i++) {
            builder.append(" ");
            builder.append(messageComponents.get(i));
        }

        final String name = builder.toString();
        final List<Vod> vods = playersController.listVodsForPlayer(name, region);

        if (vods.isEmpty()){
            return EmbeddedMessageHelper.createErrorMessage(messageChannel, String.format("No VODs found for %s, region: %s", name, region));
        }

        final PlayerShort player = getPlayer(vods.get(0).getMatch(), name);
        final StringBuilder stringBuilder = new StringBuilder();

        for (final Vod vod : vods) {
            stringBuilder.append(vod.toString()).append("\n");
        }
        stringBuilder.append("Only the latest vods are shown, for a full list, click the bolded header");
        final String description = stringBuilder.toString();
        LOG.info(description);


        final String title = String.format(HEADER, player.getName(), region.toUpperCase());
        final String vodsURL = UrlUtil.createUrl(
            ausmashConfig.getSiteUrl(), "players", Integer.toString(player.getId()), player.getName(), "videos");

        return EmbeddedMessageHelper.createMessage(messageChannel, description, title, vodsURL);
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

    @Override
    public Mono<Message> help(Mono<MessageChannel> messageChannel) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s%s [%s] [%s]\n\n",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            ParameterUtil.PLAYER_NAME,
            ParameterUtil.REGION_SHORT
        ));

        stringBuilder.append("Returns the latest vods for the given player\n");

        stringBuilder.append(String.format(
            "e.g. %s%s %s %s",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME,
            "Wontonz",
            Region.RegionType.NZ.name()
        ));

        return EmbeddedMessageHelper.createMessage(messageChannel, stringBuilder.toString());
    }
}