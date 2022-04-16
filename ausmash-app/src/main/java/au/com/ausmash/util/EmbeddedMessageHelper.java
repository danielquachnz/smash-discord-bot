package au.com.ausmash.util;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

public class EmbeddedMessageHelper {
    public static Mono<Message> createErrorMessage(Mono<MessageChannel> messageChannel, String description) {
        return messageChannel.flatMap(channel ->
            channel.createMessage(
                EmbedCreateSpec.builder()
                    .description(description)
                    .color(discord4j.rest.util.Color.RED)
                    .build()));
    }

    public static Mono<Message> createMessage(Mono<MessageChannel> messageChannel, String description) {
        return messageChannel.flatMap(channel ->
            channel.createMessage(
                 EmbedCreateSpec.builder()
                    .description(description)
                    .build()));
    }

    public static Mono<Message> createMessage(Mono<MessageChannel> messageChannel, String description, String title) {
        return messageChannel.flatMap(channel ->
            channel.createMessage(
                EmbedCreateSpec.builder()
                    .description(description)
                    .title(title)
                    .build())
        );
    }

    public static Mono<Message> createMessage(Mono<MessageChannel> messageChannel, String description, String title, String url) {
        return messageChannel.flatMap(channel ->
            channel.createMessage(
                EmbedCreateSpec.builder()
                    .description(description)
                    .title(title)
                    .url(url)
                    .build())
        );
    }
}
