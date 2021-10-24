package au.com.ausmash;

import java.util.TimeZone;

import au.com.ausmash.config.AusmashConfig;
import au.com.ausmash.config.DiscordConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages = {"au.com.ausmash"})
@EnableConfigurationProperties({
    DiscordConfig.class, AusmashConfig.class
})
public class Start {

    private static final Logger LOG = LoggerFactory.getLogger(Start.class);

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ObjectWriter objectWriter() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writerWithDefaultPrettyPrinter();
    }

    public static void main(String[] args) {

        LOG.debug("Starting ...");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        LOG.info("Current timezone is set to {}", TimeZone.getDefault().getID());

        SpringApplication.run(Start.class, args);

        LOG.debug("Started.");
    }
}