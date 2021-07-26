package com.example.salvo.dtos;

import com.example.salvo.GameState;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class GameViewDto {


    private GameState gameState;
    private  Long id;
    private  Date created;
    private  List<GamePlayerDto> gamePlayers;
    private  List<ShipDto> ships;
    private  List<SalvoDto> salvoes;
    private  HitDto hits;


    public GameViewDto(GamePlayer gamePlayer) {
        this.gameState = getGameState(gamePlayer);
        this.id = gamePlayer.getGame().getGameid();
        this.created = gamePlayer.getDate();
        this.gamePlayers = gamePlayer.getGame().getGamePlayers().stream().map(GamePlayerDto::new).collect(Collectors.toList());
        this.ships = gamePlayer.getShips().stream().map(ShipDto::new).collect(Collectors.toList());
        this.salvoes = gamePlayer.getGame().getGamePlayers().stream().flatMap(sv -> sv.getSalvoes().stream().map(SalvoDto::new)).collect(Collectors.toList());
        this.hits = new HitDto(gamePlayer);

    }


    public GameState getGameState(GamePlayer gamePlayer) {

        if (gamePlayer.getShips().size() == 0) {
            return GameState.PLACESHIPS;
        }
        if (util.opponent(gamePlayer).isEmpty()) {
            return GameState.WAITINGFOROPP;

        }

        if (util.opponent(gamePlayer).get().getShips().size() == 0) {
            return GameState.WAIT;
        }
        if (gamePlayer.getSalvoes().size() > util.opponent(gamePlayer).get().getSalvoes().size()) {
            return GameState.WAIT;
        }
            List<String> selfS = gamePlayer.getShips().stream().flatMap(s -> s.getShipLocations().stream()).collect(Collectors.toList());
            List<String> oppS = util.opponent(gamePlayer).get().getShips().stream().flatMap(s -> s.getShipLocations().stream()).collect(Collectors.toList());

            List<String> selfSal = gamePlayer.getSalvoes().stream().flatMap(sv -> sv.getSalvoLocations().stream()).collect(Collectors.toList());
            List<String> oppSal = util.opponent(gamePlayer).get().getSalvoes().stream().flatMap(sv -> sv.getSalvoLocations().stream()).collect(Collectors.toList());

            List<String> selfH = oppS.stream().filter(selfSal::contains).collect(Collectors.toList());
            List<String> oppH = selfS.stream().filter(oppSal::contains).collect(Collectors.toList());


        if (oppH.size() == selfH.size() && selfH.size() == oppS.size()) {

            return GameState.TIE;
        }
        if (selfS.size() == oppH.size()) {

            return GameState.LOSE;
        }
        if (oppS.size() == selfH.size()) {
            return GameState.WIN;
        }

        return GameState.PLAY;

    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public List<GamePlayerDto> getGamePlayers() {
        return gamePlayers;
    }

    public List<ShipDto> getShips() {
        return ships;
    }

    public List<SalvoDto> getSalvoes() {
        return salvoes;
    }

    public HitDto getHits() {
        return hits;
    }
}
