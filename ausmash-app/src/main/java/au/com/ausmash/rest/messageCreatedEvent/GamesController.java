package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;
import au.com.ausmash.model.Game;
import au.com.ausmash.model.Region;

public interface GamesController {
    String PATH = "games";
    Game findByShortName(String gameShortname);
    List<Game> listAll();
}