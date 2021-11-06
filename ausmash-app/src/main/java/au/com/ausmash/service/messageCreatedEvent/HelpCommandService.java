package au.com.ausmash.service.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.service.CommandService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class HelpCommandService implements CommandService {
    static final String COMMAND_NAME = "help";
    private static final String COMMAND_LIST_HEADER = "=====Supported Commands=====\n";
    private static final String COMMAND_LIST_FOOTER = String.format(
        "\nType %s%s [COMMAND] to get details about the command",
        MessageCreatedEventService.COMMAND_PREFIX,
        COMMAND_NAME
    );
    private static final String TOO_MANY_PARAMS_ERROR = String.format("Only expected one command word after %s", COMMAND_NAME);

    @Autowired
    private MessageCreatedEventMapperService messageCreatedEventMapperService;
    
    @Override
    public Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents) {
        if (messageComponents.isEmpty()) {
            return getAvailableCommands(messageChannel);
        }

        if (messageComponents.size() > 1) {
            return messageChannel.flatMap(channel -> channel.createMessage(TOO_MANY_PARAMS_ERROR));
        }
        return messageCreatedEventMapperService.getCommandService(messageComponents.get(0)).help(messageChannel);
    }

    private Mono<Message> getAvailableCommands(Mono<MessageChannel> messageChannel) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(COMMAND_LIST_HEADER);
        messageCreatedEventMapperService.getCommands().stream()
            .forEachOrdered(command ->
                stringBuilder
                    .append(command)
                    .append("\n")
        );

        stringBuilder.append(String.format(
            "\nTry %s%s [command] to get more information",
            MessageCreatedEventService.COMMAND_PREFIX,
            COMMAND_NAME
        ));

        return messageChannel.flatMap(channel -> channel.createMessage(stringBuilder.toString()));
    }
}