package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Elo;
import au.com.ausmash.model.Player;
import au.com.ausmash.model.Vod;
import au.com.ausmash.model.WinRate;
import au.com.ausmash.model.validator.RegionValidator;
import au.com.ausmash.rest.AbstractAusmashController;
import au.com.ausmash.rest.exception.ResourceNotFoundException;
import au.com.ausmash.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
class PlayersControllerImpl extends AbstractAusmashController implements PlayersController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayersControllerImpl.class);
    private static final int VOD_LIMIT = 3;

    @Override
    public Player find(String name, String region) {
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(PlayersController.PATH),"find", name, region);
        LOG.info(String.format("player url = %s", url));
        try {
            RegionValidator.validateShortName(region);
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
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new ResourceNotFoundException(String.format(
                    "The player \"%s\" of region \"%s\" could not be found on Ausmash. Please check your details", name, region
                ));
            }
            throw e;
        }
    }

    @Override
    public List<Elo> getEloForPlayer(String name, String region) {
        final Player player = find(name, region);
        final String id = Integer.toString(player.getId());
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(PlayersController.PATH),id, "elo");
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

    @Override
    public List<Vod> getVodsForPlayer(String name, String region) {
        final Player player = find(name, region);
        final String id = Integer.toString(player.getId());
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(PlayersController.PATH),id, "videos");
        final ResponseEntity<Vod[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Vod[].class
        );

        final List<Vod> vods = Arrays.stream(Optional.ofNullable(response.getBody()).orElse(new Vod[0]))
            .sorted((r1, r2) -> r2.getMatch().getTourney().getTourneyDate().compareTo(r1.getMatch().getTourney().getTourneyDate()))
            .collect(Collectors.toList());
        if (vods.size() > VOD_LIMIT) {
            return vods.subList(0, VOD_LIMIT);
        }
        return vods;
    }

    @Override
    public List<WinRate> getWinratesForPlayerAndGame(int playerId, int gameId) {
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(PlayersController.PATH),
            Integer.toString(playerId),
            "winrates",
            Integer.toString(gameId)
        );
        final ResponseEntity<WinRate[]> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            WinRate[].class
        );
        return Arrays.stream(Optional.ofNullable(response.getBody()).orElse(new WinRate[0]))
            .collect(Collectors.toList());
    }
}