package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;
import au.com.ausmash.model.Tourney;

public interface TourneysController {
    String PATH = "tourneys";
    Tourney getById(int id);
    List<Tourney> listUpcoming();
}