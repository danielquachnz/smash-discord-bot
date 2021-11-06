package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Ranking;
import au.com.ausmash.model.validator.GameValidator;
import au.com.ausmash.model.validator.RegionValidator;
import au.com.ausmash.rest.AbstractAusmashController;
import au.com.ausmash.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RankingsControllerImpl extends AbstractAusmashController implements RankingsController {

    @Override
    public Ranking getById(int id) {
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(RankingsController.PATH),
            Integer.toString(id)
        );
        final ResponseEntity<Ranking> ranking = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Ranking.class
        );

        if (ranking.getStatusCode().is2xxSuccessful()){
            return ranking.getBody();
        }

        return null;
    }

    @Override
    public List<Ranking> listByRegionAndGame(String regionCode, String gameCode) {
        RegionValidator.validateShortName(regionCode);
        GameValidator.validateShortName(gameCode);

        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(RankingsController.PATH),
            "byregion",
            regionCode
        );

        final ResponseEntity<Ranking[]> rankings = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Ranking[].class
        );

        if (rankings.getStatusCode().is2xxSuccessful()){
            return Arrays.stream(Optional.ofNullable(rankings.getBody()).orElse(new Ranking[0]))
                .filter(r -> StringUtils.equalsIgnoreCase(r.getGameShort().name(), gameCode))
                .map(t -> getById(t.getId()))
                .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

}