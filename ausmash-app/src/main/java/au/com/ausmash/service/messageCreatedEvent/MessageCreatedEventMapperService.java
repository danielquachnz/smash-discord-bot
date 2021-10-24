package au.com.ausmash.service.messageCreatedEvent;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

import au.com.ausmash.service.CommandService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MessageCreatedEventMapperService {
    @Autowired
    private PingCommandService pingCommandService;
    @Autowired
    private ChannelsCommandService channelsCommandService;
    @Autowired
    private RegionsCommandService regionsCommandService;
    @Autowired
    private PlayerCommandService playerCommandService;
    @Autowired
    private EloCommandService eloCommandService;
    @Autowired
    private GamesCommandService gamesCommandService;

    private Map<String, CommandService> serviceMap;

    @PostConstruct
    void postConstruct() {
        serviceMap = new ImmutableMap.Builder<String, CommandService>()
            .put(PingCommandService.COMMAND_NAME, pingCommandService)
            .put(ChannelsCommandService.COMMAND_NAME, channelsCommandService)
            .put(RegionsCommandService.COMMAND_NAME, regionsCommandService)
            .put(PlayerCommandService.COMMAND_NAME, playerCommandService)
            .put(EloCommandService.COMMAND_NAME, eloCommandService)
            .put(GamesCommandService.COMMAND_NAME, gamesCommandService)
            .build();
    }

    Optional<CommandService> getCommandService(final String command) {
        return Optional.ofNullable(serviceMap.get(command));
    }
}