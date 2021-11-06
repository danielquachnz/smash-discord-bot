package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Region {
    private int id;
    private String name;
    private String apiLink;
    @JsonProperty("Short")
    private RegionType regionType;

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

    public RegionType getRegionType() {
        return regionType;
    }

    public void setShort(RegionType shortName) {
        this.regionType = shortName;
    }

    @Override
    public String toString() {
        return getName().concat(": ").concat(getRegionType().name());
    }

    public enum RegionType {
        ACT("Australian Capital Territory"),
        INT("International"),
        NSW("New South Wales"),
        NZ("New Zealand"),
        NT("Northern Territory"),
        QLD("Queensland"),
        SA("South Australia"),
        TAS("Tasmania"),
        VIC("Victoria"),
        WA("Western Australia");

        private String longName;

        RegionType(String longName) {
            this.longName = longName;
        }

        public String getLongName() {
            return longName;
        }

    }
}