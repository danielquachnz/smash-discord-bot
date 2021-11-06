package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Game {
    private int id;
    private String name;
    @JsonProperty("Short")
    private GameType gameType;
    private int sortOrder;
    private String apiLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, gameType.name());
    }

    public enum GameType {
        SSB64(1),
        SSBM(2),
        SSBB(3),
        PM(5),
        SSB3DS(11),
        SSBWU(12),
        SSBU(13);

        private int id;

        GameType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}