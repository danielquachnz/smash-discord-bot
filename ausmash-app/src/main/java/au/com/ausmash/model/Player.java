package au.com.ausmash.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Player {
    private int id;
    private String name;
    private String regionShort;
    private String personalUrl;
    private String twitchUrl;
    private String youtubeUrl;
    private String twitterUrl;
    private String facebookUrl;
    private String ssbworldUrl;
    private String bio;
    private int tournamentCount;
    private int matchCount;
    @JsonIgnore
    private int resultCount;
    private int videoCount;

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

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setPersonalUrl(String personalUrl) {
        this.personalUrl = personalUrl;
    }

    public String getTwitchUrl() {
        return twitchUrl;
    }

    public void setTwitchUrl(String twitchUrl) {
        this.twitchUrl = twitchUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getSsbworldUrl() {
        return ssbworldUrl;
    }

    public void setSsbworldUrl(String ssbworldUrl) {
        this.ssbworldUrl = ssbworldUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTournamentCount() {
        return tournamentCount;
    }

    public void setTournamentCount(int tournamentCount) {
        this.tournamentCount = tournamentCount;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(personalUrl)) {
            builder.append(String.format("Personal Url: %s\n", personalUrl));
        }
        if (StringUtils.isNotBlank(twitchUrl)) {
            builder.append(String.format("Twitch Url: %s\n", twitchUrl));
        }
        if (StringUtils.isNotBlank(youtubeUrl)) {
            builder.append(String.format("YouTube Url: %s\n", youtubeUrl));
        }
        if (StringUtils.isNotBlank(twitterUrl)) {
            builder.append(String.format("Twitter Url: %s\n", twitterUrl));
        }
        if (StringUtils.isNotBlank(facebookUrl)) {
            builder.append(String.format("Facebook Url: %s\n", facebookUrl));
        }
        if (StringUtils.isNotBlank(ssbworldUrl)) {
            builder.append(String.format("SSB World Url: %s\n", ssbworldUrl));
        }
        if (StringUtils.isNotBlank(bio)) {
            builder.append(String.format("Bio: %s\n", bio));
        }

        builder.append(String.format("Tournament     Count: %d\n", tournamentCount));
        builder.append(String.format("Match Count: %d\n", matchCount));
        builder.append(String.format("Video Count: %d\n", videoCount));
        return builder.toString();
    }
}