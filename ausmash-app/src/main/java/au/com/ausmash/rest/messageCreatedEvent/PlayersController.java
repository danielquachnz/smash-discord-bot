package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Elo;
import au.com.ausmash.model.Player;

public interface PlayersController {
    String PATH = "players";
    Player find(String name, String region);

    List<Elo> getEloForPlayer(String name, String region);
}