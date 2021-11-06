package au.com.ausmash.rest.messageCreatedEvent;

import java.util.List;

import au.com.ausmash.model.Ranking;
import au.com.ausmash.model.Region;
import au.com.ausmash.model.Tourney;

public interface RankingsController {
    String PATH = "rankings";
    Ranking getById(int id);
    List<Ranking> listByRegionAndGame(String regionCode, String gameCode);
}