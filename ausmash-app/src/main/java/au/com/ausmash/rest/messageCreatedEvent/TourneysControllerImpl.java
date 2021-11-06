package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Tourney;
import au.com.ausmash.rest.AbstractAusmashController;
import au.com.ausmash.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TourneysControllerImpl extends AbstractAusmashController implements TourneysController {

    @Override
    public Tourney getById(int id) {
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(TourneysController.PATH),Integer.toString(id));
        final ResponseEntity<Tourney> tourney = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Tourney.class
        );

        if (tourney.getStatusCode().is2xxSuccessful()){
            return tourney.getBody();
        }

        return null;
    }

    @Override
    public List<Tourney> listUpcoming() {
        final String url = UrlUtil.createUrl(
            getAusmashApiUrl(TourneysController.PATH),"upcoming");
        final ResponseEntity<Tourney[]> upcomingTourneys = restTemplate.exchange(
            url,
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Tourney[].class
        );

        if (upcomingTourneys.getStatusCode().is2xxSuccessful()){
            return Arrays.stream(Optional.ofNullable(upcomingTourneys.getBody()).orElse(new Tourney[0]))
                .map(t -> getById(t.getId()))
                .sorted(sortByRegionAndDate())
                .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private Comparator<Tourney> sortByRegionAndDate() {
        return (t1, t2) -> {
            int byRegion = StringUtils.compareIgnoreCase(t1.getRegionShort().name(), t2.getRegionShort().name());

            if (byRegion != 0) {
                return byRegion;
            }
            return t1.getTourneyDate().compareTo(t2.getTourneyDate());
        };
    }
}