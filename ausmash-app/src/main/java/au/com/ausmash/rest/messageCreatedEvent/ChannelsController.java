package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.ausmash.model.Channel;
import org.springframework.web.bind.annotation.GetMapping;

public interface ChannelsController {
    String PATH = "channels";
    List<Channel> listAll();
}