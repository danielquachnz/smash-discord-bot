package au.com.ausmash.rest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import au.com.ausmash.config.AusmashConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractAusmashController {
    @Autowired
    protected RestTemplate restTemplate;

    private static final String API_KEY_HEADER = "X-ApiKey";

    @Autowired
    private AusmashConfig ausmashConfig;

    protected HttpEntity<String> getAusmashApiKeyHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY_HEADER, ausmashConfig.getApiKey());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

    protected String getAusmashUrl(String path) {
        return ausmashConfig.getUrl().concat(path);
    }

    protected String createUrl(String url, String... paths) {
        final Optional<String> oPath = Arrays.stream(paths).reduce((path1, path2) -> path1.concat("/").concat(path2));
        if (oPath.isPresent()) {
            return url.concat("/").concat(oPath.get());
        }
        return url;
    }
}