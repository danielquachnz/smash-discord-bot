package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Channel;
import au.com.ausmash.model.Game;

public interface GamesController {
    String PATH = "games";
    List<Game> listAll();
}