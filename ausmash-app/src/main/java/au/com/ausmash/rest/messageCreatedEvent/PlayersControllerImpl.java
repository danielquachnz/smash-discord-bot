package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Elo;
import au.com.ausmash.model.Player;
import au.com.ausmash.model.Region;
import au.com.ausmash.rest.AbstractAusmashController;
import au.com.ausmash.service.messageCreatedEvent.PlayerCommandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayersControllerImpl extends AbstractAusmashController implements PlayersController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayersControllerImpl.class);

    @Override
    public Player find(String name, String region) {
        final String url = createUrl(
            getAusmashUrl(PlayersController.PATH),"find", name, region);
        LOG.info(String.format("player url = %s", url));
        final ResponseEntity<Player> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Player.class
        );

        if (response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }

        return null;

    }

    @Override
    public List<Elo> getEloForPlayer(String name, String region) {
        final Player player = find(name, region);
        final String id = Integer.toString(player.getId());
        final String url = createUrl(
            getAusmashUrl(PlayersController.PATH),id, "elo");
        final ResponseEntity<Elo[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Elo[].class
        );

        return Arrays.stream(Optional.ofNullable(response.getBody()).orElse(new Elo[0]))
            .sorted((r1, r2) -> StringUtils.compareIgnoreCase(r1.toString(), r2.toString()))
            .collect(Collectors.toList());
    }
}