package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Region {
    private int id;
    private String name;
    private String apiLink;
    @JsonProperty("Short")
    private String shortName;

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

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShort(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return getName().concat(": ").concat(getShortName());
    }
}