package au.com.ausmash.service;

import javax.annotation.PostConstruct;

import au.com.ausmash.config.DiscordConfig;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GatewayService {
    @Autowired
    private MessageCreatedEventService messageCreatedEventService;
    @Autowired
    private DiscordConfig discordConfig;

    @PostConstruct
    void postConstruct() {
        DiscordClient.create(discordConfig.getApiKey())
            .withGateway((GatewayDiscordClient gateway) ->
                gateway.on(
                    MessageCreateEvent.class,
                    event ->  messageCreatedEventService.process(event)
                )
            )
            .block();
    }
}