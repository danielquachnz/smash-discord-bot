package au.com.ausmash.model;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class PlayerRank {
    private int rank;
    private PlayerShort player;
    private String rankingScale;
    private List<Character> characters;
    private Game.GameType gameShort;
    private String apiLink;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public PlayerShort getPlayer() {
        return player;
    }

    public void setPlayer(PlayerShort player) {
        this.player = player;
    }

    public String getRankingScale() {
        return rankingScale;
    }

    public void setRankingScale(String rankingScale) {
        this.rankingScale = rankingScale;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public Game.GameType getGameShort() {
        return gameShort;
    }

    public void setGameShort(Game.GameType gameShort) {
        this.gameShort = gameShort;
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

        stringBuilder.append(String.format(
            "%d. %s (%s)\n",
            rank,
            player.getName(),
            player.getRegionShort()
        ));

        characters.stream().map(Character::getName)
                .reduce((c1, c2) -> c1.concat(", ").concat(c2))
                .ifPresent(s -> stringBuilder.append("Characters: ").append(s).append("\n"));
        return stringBuilder.toString();
    }
}