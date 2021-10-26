package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Event {
    private int id;
    private String name;
    private String bracketStyle;
    private String eventType;
    private Game game;

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

    public String getBracketStyle() {
        return bracketStyle;
    }

    public void setBracketStyle(String bracketStyle) {
        this.bracketStyle = bracketStyle;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("Name: %s\n", name));
        builder.append(String.format("Bracket Style: %s\n", bracketStyle));
        builder.append(String.format("Event Type: %s\n", eventType));
        builder.append(String.format("Game: %s\n", game.toString()));
        return builder.toString();
    }
}