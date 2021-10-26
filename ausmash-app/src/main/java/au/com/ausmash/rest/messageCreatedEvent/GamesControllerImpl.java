package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Game;
import au.com.ausmash.model.Game;
import au.com.ausmash.rest.AbstractAusmashController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class GamesControllerImpl extends AbstractAusmashController implements GamesController {
    @Override
    public List<Game> listAll() {
        final ResponseEntity<Game[]> games = restTemplate.exchange(
            getAusmashUrl(GamesController.PATH),
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Game[].class
        );

        if (games.getStatusCode().is2xxSuccessful()){
            return Arrays.stream(Optional.ofNullable(games.getBody()).orElse(new Game[0]))
                .sorted((c1, c2) -> StringUtils.compareIgnoreCase(c1.toString(), c2.toString()))
                .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}