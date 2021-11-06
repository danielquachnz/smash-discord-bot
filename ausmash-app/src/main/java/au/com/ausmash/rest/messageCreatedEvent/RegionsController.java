package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Region;

public interface RegionsController {
    String PATH = "regions";
    List<Region> listAll();
}