package au.com.ausmash.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public interface CommandService {
    Mono<Message> processMessage(Mono<MessageChannel> messageChannel, List<String> messageComponents);
}