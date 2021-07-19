package com.example.salvo.dtos;

import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;

public class GamePlayerDto {
    private long id;
    //funciono!
    private PlayerDto player;




    public GamePlayerDto(GamePlayer gamePlayer) {
        this.id = gamePlayer.getGamePlayerid();
        this.player = new PlayerDto(gamePlayer.getPlayer());
    }

    public long getId() {
        return id;
    }

    public PlayerDto getPlayer() {
        return player;
    }
}
