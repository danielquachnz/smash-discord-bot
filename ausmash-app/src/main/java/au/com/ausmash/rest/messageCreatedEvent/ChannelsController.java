package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;

public interface ChannelsController {
    String PATH = "channels";
    List<Channel> listAll();
}