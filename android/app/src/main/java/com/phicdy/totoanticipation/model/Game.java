package com.phicdy.totoanticipation.model;

public class Game {
    public final String homeTeam;
    public final String awayTeam;
    public String homeRanking;
    public String awayRanking;
    public Anticipation anticipation = Anticipation.HOME;
    public enum Anticipation {HOME, AWAY, DRAW};

    public Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
