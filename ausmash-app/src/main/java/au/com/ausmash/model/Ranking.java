package au.com.ausmash.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Ranking {
    private int id;
    private String version;
    private String sequenceName;
    private String link;
    private List<PlayerRank> players;
    private Game.GameType gameShort;
    private Region.RegionType regionShort;
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

    public Game.GameType getGameShort() {
        return gameShort;
    }

    public void setGameShort(Game.GameType gameShort) {
        this.gameShort = gameShort;
    }

    public Region.RegionType getRegionShort() {
        return regionShort;
    }

    public void setRegionShort(Region.RegionType regionShort) {
        this.regionShort = regionShort;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<PlayerRank> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerRank> players) {
        this.players = players;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (players != null) {
            players.stream().map(PlayerRank::toString)
                .reduce((p1, p2) -> p1.concat("\n").concat(p2))
                .ifPresent(s -> stringBuilder.append(s).append("\n"));
        }

        if (StringUtils.isNotBlank(link)){
            stringBuilder.append(String.format("Link: %s", link));
        }

        return stringBuilder.toString();
    }
}