package com.example.salvo.dtos;

import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GameDto {
    private long id;
    private Date created;

    private Set<GamePlayerDto> gamePlayers = new HashSet<>();

    private Set<ScoreDto> scores = new HashSet<>();



    public GameDto(Game game){
        this.id = game.getGameid();
        this.created = game.getDate();
        this.gamePlayers =  game.getGamePlayers().stream().map(gp -> new GamePlayerDto(gp)).collect(Collectors.toSet());
        this.scores =  game.getGamePlayers().stream().map(score -> new ScoreDto(score)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }


    public Set<GamePlayerDto> getGamePlayers() {
        return gamePlayers;
    }

    public Set<ScoreDto> getScores() {
        return scores;
    }
}
