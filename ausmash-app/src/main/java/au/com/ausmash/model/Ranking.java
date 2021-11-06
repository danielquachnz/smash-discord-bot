package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Ranking {
    private int id;
    private String version;
    private String sequenceName;
    private Game.GameType gameType;
    private Region.RegionType regionType;
    private String apiLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Game.GameType getGameType() {
        return gameType;
    }

    public void setGameType(Game.GameType gameType) {
        this.gameType = gameType;
    }

    public Region.RegionType getRegionType() {
        return regionType;
    }

    public void setRegionType(Region.RegionType regionType) {
        this.regionType = regionType;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }
}