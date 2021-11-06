package au.com.ausmash.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import au.com.ausmash.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Tourney {
    private int id;
    private String name;
    private String regionShort;
    private Date tourneyDate;
    private boolean isMajor;
    private List<Event> events;
    private String apilink;

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

    public String getRegionShort() {
        return regionShort;
    }

    public void setRegionShort(String regionShort) {
        this.regionShort = regionShort;
    }

    public Date getTourneyDate() {
        return tourneyDate;
    }

    public void setTourneyDate(Date tourneyDate) {
        this.tourneyDate = tourneyDate;
    }

    public boolean isMajor() {
        return isMajor;
    }

    public void setMajor(boolean major) {
        isMajor = major;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getApilink() {
        return apilink;
    }

    public void setApilink(String apilink) {
        this.apilink = apilink;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("Tournament: %s", name));
        if (isMajor) {
            builder.append(" (Major)");
        }

        if (events != null) {
            final Optional<String> eventsString = events.stream().map(Event::getName).reduce((e1,e2) -> e1.concat(",").concat(e2));
            eventsString.ifPresent(e -> builder.append("Events: ").append(e));
        }

        builder.append(String.format("\nRegion: %s\n", regionShort));
        builder.append(String.format("Date: %s\n", DateUtil.dateToString(tourneyDate)));
        return builder.toString();
    }
}