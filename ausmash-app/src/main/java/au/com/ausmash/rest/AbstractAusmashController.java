package au.com.ausmash.rest;

import java.util.Collections;

import au.com.ausmash.config.AusmashConfig;
import au.com.ausmash.util.UrlUtil;
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

    protected String getAusmashApiUrl(String path) {
        return UrlUtil.createUrl(ausmashConfig.getApiUrl(), path);
    }
}