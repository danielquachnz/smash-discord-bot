package au.com.ausmash.service.messageCreatedEvent;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService.UNRECOGNISED_COMMAND;

import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.CommandService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MessageCreatedEventMapperService {
    @Autowired
    private ChannelsCommandService channelsCommandService;
    @Autowired
    private EloCommandService eloCommandService;
    @Autowired
    private GamesCommandService gamesCommandService;
    @Autowired
    private HelpCommandService helpCommandService;
    @Autowired
    private NzRegionsCommandService nzRegionsCommandService;
    @Autowired
    private PingCommandService pingCommandService;
    @Autowired
    private PlayerCommandService playerCommandService;
    @Autowired
    private RegionsCommandService regionsCommandService;
    @Autowired
    private UpcomingCommandService upcomingCommandService;
    @Autowired
    private VodsCommandService vodsCommandService;
    @Autowired
    private WinratesCommandService winratesCommandService;

    private Map<String, CommandService> serviceMap;

    @PostConstruct
    void postConstruct() {
        serviceMap = new ImmutableMap.Builder<String, CommandService>()
            .put(ChannelsCommandService.COMMAND_NAME, channelsCommandService)
            .put(EloCommandService.COMMAND_NAME, eloCommandService)
            .put(GamesCommandService.COMMAND_NAME, gamesCommandService)
            .put(HelpCommandService.COMMAND_NAME, helpCommandService)
            .put(NzRegionsCommandService.COMMAND_NAME, nzRegionsCommandService)
            .put(PingCommandService.COMMAND_NAME, pingCommandService)
            .put(PlayerCommandService.COMMAND_NAME, playerCommandService)
            .put(RegionsCommandService.COMMAND_NAME, regionsCommandService)
            .put(UpcomingCommandService.COMMAND_NAME, upcomingCommandService)
            .put(VodsCommandService.COMMAND_NAME, vodsCommandService)
            .put(WinratesCommandService.COMMAND_NAME, winratesCommandService)
            .build();
    }

    CommandService getCommandService(final String command) {
        return Optional.ofNullable(serviceMap.get(command)).orElseThrow(() ->
            new ValidationException(UNRECOGNISED_COMMAND)
        );
    }

    Set<String> getCommands() {
        return serviceMap.keySet();
    }
}