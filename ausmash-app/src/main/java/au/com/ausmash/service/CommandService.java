package au.com.ausmash.service;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public interface CommandService {
    Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents);
    default Mono<Message> help(Mono<MessageChannel> messageChannel) {
        return Mono.empty();
    }
}