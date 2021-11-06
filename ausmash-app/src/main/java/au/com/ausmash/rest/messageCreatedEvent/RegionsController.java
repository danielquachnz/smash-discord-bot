package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.ausmash.model.Channel;
import au.com.ausmash.model.Region;
import org.springframework.web.bind.annotation.GetMapping;

public interface RegionsController {
    String PATH = "regions";
    Region findByShortName(String regionShortname);
    List<Region> listAll();
}