package au.com.ausmash.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Match {
    private PlayerShort winner;
    private PlayerShort loser;
    private PlayerShort teamWinner1;
    private PlayerShort teamWinner2;
    private PlayerShort teamLoser1;
    private PlayerShort teamLoser2;
    private Tourney tourney;
    private Event event;
    private String winnerName;
    private String loserName;
    private int pool;
    private List<Character> winnerCharacters;
    private List<Character> loserCharacters;
    private int eloWinnerOldScore;
    private int eloLoserOldScore;
    private int eloWinnerNewScore;
    private int eloLoserNewScore;
    private int eloMovement;

    public PlayerShort getWinner() {
        return winner;
    }

    public void setWinner(PlayerShort winner) {
        this.winner = winner;
    }

    public PlayerShort getLoser() {
        return loser;
    }

    public void setLoser(PlayerShort loser) {
        this.loser = loser;
    }

    public PlayerShort getTeamWinner1() {
        return teamWinner1;
    }

    public void setTeamWinner1(PlayerShort teamWinner1) {
        this.teamWinner1 = teamWinner1;
    }

    public PlayerShort getTeamWinner2() {
        return teamWinner2;
    }

    public void setTeamWinner2(PlayerShort teamWinner2) {
        this.teamWinner2 = teamWinner2;
    }

    public PlayerShort getTeamLoser1() {
        return teamLoser1;
    }

    public void setTeamLoser1(PlayerShort teamLoser1) {
        this.teamLoser1 = teamLoser1;
    }

    public PlayerShort getTeamLoser2() {
        return teamLoser2;
    }

    public void setTeamLoser2(PlayerShort teamLoser2) {
        this.teamLoser2 = teamLoser2;
    }

    public Tourney getTourney() {
        return tourney;
    }

    public void setTourney(Tourney tourney) {
        this.tourney = tourney;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    public int getPool() {
        return pool;
    }

    public void setPool(int pool) {
        this.pool = pool;
    }

    public List<Character> getWinnerCharacters() {
        return winnerCharacters;
    }

    public void setWinnerCharacters(List<Character> winnerCharacters) {
        this.winnerCharacters = winnerCharacters;
    }

    public List<Character> getLoserCharacters() {
        return loserCharacters;
    }

    public void setLoserCharacters(List<Character> loserCharacters) {
        this.loserCharacters = loserCharacters;
    }

    public int getEloWinnerOldScore() {
        return eloWinnerOldScore;
    }

    public void setEloWinnerOldScore(int eloWinnerOldScore) {
        this.eloWinnerOldScore = eloWinnerOldScore;
    }

    public int getEloLoserOldScore() {
        return eloLoserOldScore;
    }

    public void setEloLoserOldScore(int eloLoserOldScore) {
        this.eloLoserOldScore = eloLoserOldScore;
    }

    public int getEloWinnerNewScore() {
        return eloWinnerNewScore;
    }

    public void setEloWinnerNewScore(int eloWinnerNewScore) {
        this.eloWinnerNewScore = eloWinnerNewScore;
    }

    public int getEloLoserNewScore() {
        return eloLoserNewScore;
    }

    public void setEloLoserNewScore(int eloLoserNewScore) {
        this.eloLoserNewScore = eloLoserNewScore;
    }

    public int getEloMovement() {
        return eloMovement;
    }

    public void setEloMovement(int eloMovement) {
        this.eloMovement = eloMovement;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (winner != null) {
            builder.append(String.format("Players: %s (W) vs %s (L)\n", winner, loser));
        } else {
            builder.append(String.format("Players: %s and %s (W) vs %s and %s (L)\n",
                teamWinner1, teamWinner2, teamLoser1, teamLoser2));
        }
        builder.append(String.format("Tournament: %s (%s)", tourney.getName(), tourney.getRegionShort()));
        builder.append(String.format("Event: %s (%s)", event.getGame(), event.getEventType()));
        if (!winnerCharacters.isEmpty()) {
            builder.append("Winner characters: ");
            winnerCharacters.forEach(c -> builder.append(c.toString()).append(" "));
            builder.append("\n");
        }
        if (!loserCharacters.isEmpty()) {
            builder.append("Loser characters: ");
            loserCharacters.forEach(c -> builder.append(c.toString()).append(" "));
            builder.append("\n");
        }
        if (eloWinnerOldScore != 0) {
            builder.append(String.format("Winner ELO change: %s -> %s\n", eloWinnerOldScore, eloWinnerNewScore));
        }
        if (eloWinnerOldScore != 0) {
            builder.append(String.format("Loser ELO change: %s -> %s\n", eloLoserOldScore, eloLoserNewScore));
        }
        return builder.toString();
    }

    public String toShortString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%s) ", tourney.getName(), event.getGame().getShortName()));
        if (winner != null) {
            builder.append(String.format("%s v %s\n", winner, loser));
        } else {
            builder.append(String.format("%s + %s vs %s + %s\n",
                teamWinner1, teamWinner2, teamLoser1, teamLoser2));
        }
        return builder.toString();
    }

}