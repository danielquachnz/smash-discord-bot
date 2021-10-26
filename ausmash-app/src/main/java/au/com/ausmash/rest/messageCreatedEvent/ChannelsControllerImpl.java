package au.com.ausmash.rest.messageCreatedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import au.com.ausmash.model.Channel;
import au.com.ausmash.rest.AbstractAusmashController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChannelsControllerImpl extends AbstractAusmashController implements ChannelsController {
    @Override
    public List<Channel> listAll() {
        final ResponseEntity<Channel[]> channels = restTemplate.exchange(
            getAusmashApiUrl(ChannelsController.PATH),
            HttpMethod.GET,
            getAusmashApiKeyHeader(),
            Channel[].class
        );

        if (channels.getStatusCode().is2xxSuccessful()){
            return Arrays.stream(Optional.ofNullable(channels.getBody()).orElse(new Channel[0]))
                .sorted((c1, c2) -> StringUtils.compareIgnoreCase(c1.toString(), c2.toString()))
                .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}