package au.com.ausmash.model;

import java.util.Date;

import au.com.ausmash.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Tourney {
    private int id;
    private String name;
    private String regionShort;
    private Date tourneyDate;
    private boolean isMajor;
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
        builder.append(String.format("\nRegion: %s\n", regionShort));
        builder.append(String.format("Date: %s\n", DateUtil.dateToString(tourneyDate)));
        return builder.toString();
    }
}