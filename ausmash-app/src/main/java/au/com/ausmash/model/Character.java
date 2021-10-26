package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Character {
    private int id;
    private String name;
    private String gameShort;

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

    public String getGameShort() {
        return gameShort;
    }

    public void setGameShort(String gameShort) {
        this.gameShort = gameShort;
    }

    @Override
    public String toString() {
        return name;
    }
}