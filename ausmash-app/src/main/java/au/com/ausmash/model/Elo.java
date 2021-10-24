package au.com.ausmash.model;

import java.util.Date;

import static au.com.ausmash.util.DateUtil.dateToString;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Elo {
    private int id;
    private Game game;
    private Player player;
    private int elo;
    private int eloPrevious;
    private int rankNational;
    private int rankLocal;
    private int rankNationalPrevious;
    private int rankLocalPrevious;
    private int rankNationalMovement;
    private int rankLocalMovement;
    private int rankNationalPeak;
    private int rankLocalPeak;
    private int eloMovement;
    private Date peakDate;
    private int peakElo;
    private Date lastActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getEloPrevious() {
        return eloPrevious;
    }

    public void setEloPrevious(int eloPrevious) {
        this.eloPrevious = eloPrevious;
    }

    public int getRankNational() {
        return rankNational;
    }

    public void setRankNational(int rankNational) {
        this.rankNational = rankNational;
    }

    public int getRankLocal() {
        return rankLocal;
    }

    public void setRankLocal(int rankLocal) {
        this.rankLocal = rankLocal;
    }

    public int getRankNationalPrevious() {
        return rankNationalPrevious;
    }

    public void setRankNationalPrevious(int rankNationalPrevious) {
        this.rankNationalPrevious = rankNationalPrevious;
    }

    public int getRankLocalPrevious() {
        return rankLocalPrevious;
    }

    public void setRankLocalPrevious(int rankLocalPrevious) {
        this.rankLocalPrevious = rankLocalPrevious;
    }

    public int getRankNationalMovement() {
        return rankNationalMovement;
    }

    public void setRankNationalMovement(int rankNationalMovement) {
        this.rankNationalMovement = rankNationalMovement;
    }

    public int getRankLocalMovement() {
        return rankLocalMovement;
    }

    public void setRankLocalMovement(int rankLocalMovement) {
        this.rankLocalMovement = rankLocalMovement;
    }

    public int getRankNationalPeak() {
        return rankNationalPeak;
    }

    public void setRankNationalPeak(int rankNationalPeak) {
        this.rankNationalPeak = rankNationalPeak;
    }

    public int getRankLocalPeak() {
        return rankLocalPeak;
    }

    public void setRankLocalPeak(int rankLocalPeak) {
        this.rankLocalPeak = rankLocalPeak;
    }

    public int getEloMovement() {
        return eloMovement;
    }

    public void setEloMovement(int eloMovement) {
        this.eloMovement = eloMovement;
    }

    public Date getPeakDate() {
        return peakDate;
    }

    public void setPeakDate(Date peakDate) {
        this.peakDate = peakDate;
    }

    public int getPeakElo() {
        return peakElo;
    }

    public void setPeakElo(int peakElo) {
        this.peakElo = peakElo;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("Game: %s", game.toString()));
        if (elo != 0) {
            builder.append(String.format("ELO: %d\n", elo));
        }

        if (eloPrevious != 0) {
            builder.append(String.format("Previous ELO: %d\n", eloPrevious));
        }

        if (rankNational != 0) {
            builder.append(String.format("National Rank: %d\n", rankNational));
        }

        if (rankLocal != 0) {
            builder.append(String.format("Local Rank: %d\n", rankLocal));
        }

        if (rankNationalPrevious != 0) {
            builder.append(String.format("Previous National Rank: %d\n", rankNationalPrevious));
        }

        if (rankLocalPrevious != 0) {
            builder.append(String.format("Previous Local Rank: %d\n", rankLocalPrevious));
        }

        if (rankNationalMovement != 0) {
            builder.append(String.format("National Rank Movement: %d\n", rankNationalMovement));
        }

        if (rankLocalMovement != 0) {
            builder.append(String.format("Local Rank Movement: %d\n", rankLocalMovement));
        }

        if (rankNationalPeak != 0) {
            builder.append(String.format("Peak National Rank: %d\n", rankNationalPeak));
        }

        if (rankLocalPeak != 0) {
            builder.append(String.format("Peak Local Rank: %d\n", rankLocalPeak));
        }

        if (eloMovement != 0) {
            builder.append(String.format("Elo Movement: %d\n", eloMovement));
        }

        if (peakDate != null) {
            builder.append(String.format("Peak ELO Date: %s\n", dateToString(peakDate)));
        }

        if (peakElo != 0) {
            builder.append(String.format("Peak ELO: %d\n", peakElo));
        }

        if (lastActive != null) {
            builder.append(String.format("Last Active: %s\n", dateToString(lastActive)));
        }

        return builder.toString();
    }
}