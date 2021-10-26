package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Region;
import au.com.ausmash.rest.AbstractAusmashController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RegionsControllerImpl extends AbstractAusmashController implements RegionsController {

    @Override
    public List<Region> listAll() {
        final ResponseEntity<Region[]> regions = restTemplate.exchange(
            getAusmashApiUrl(RegionsController.PATH),
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Region[].class
        );

        if (regions.getStatusCode().is2xxSuccessful()){
            return Arrays.stream(Optional.ofNullable(regions.getBody()).orElse(new Region[0]))
                .sorted((r1, r2) -> StringUtils.compareIgnoreCase(r1.toString(), r2.toString()))
                .collect(Collectors.toList());
        }

        return Collections.emptyList();

    }
}