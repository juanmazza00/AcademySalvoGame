package com.example.salvo.dtos;

import com.example.salvo.Models.GamePlayer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GameViewDto {
    private String gameState;
    private Long id;
    private Date created;
    private Set<GamePlayerDto> gamePlayers = new HashSet<>();
    private Set<ShipDto> ships = new HashSet<>();
    private Set<SalvoDto> salvoes = new HashSet<>();
    private HitDto hits ;


    public GameViewDto(GamePlayer GameView){
        this.gameState = "PLACESHIPS";
        this.id = GameView.getGame().getGameid();
        this.created = GameView.getDate();
        this.gamePlayers = GameView.getGame().getGamePlayers().stream().map(gmp -> new GamePlayerDto(gmp)).collect(Collectors.toSet());
        this.ships = GameView.getShips().stream().map(ShipDto::new).collect(Collectors.toSet());
        this.salvoes = GameView.getGame().getGamePlayers().stream().flatMap(sv -> sv.getSalvoes().stream().map(sp -> new SalvoDto(sp))).collect(Collectors.toSet());
        this.hits = new HitDto();
    }



    public String getGameState() {
        return gameState;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Set<GamePlayerDto> getGamePlayers() {
        return gamePlayers;
    }

    public Set<ShipDto> getShips() {
        return ships;
    }

    public Set<SalvoDto> getSalvoes() {
        return salvoes;
    }

    public HitDto getHits() {
        return hits;
    }
}
