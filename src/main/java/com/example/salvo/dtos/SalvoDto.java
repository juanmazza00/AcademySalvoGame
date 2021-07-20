package com.example.salvo.dtos;

import com.example.salvo.Models.Salvo;

import java.util.List;

public class SalvoDto {
    private Long player;
    private int turn;
    private List<String> Locations;


    public SalvoDto(Salvo salvo) {
        this.player = salvo.getGamePlayer().getPlayer().getUserid();
        this.turn = salvo.getTurn();
        this.Locations = salvo.getSalvoLocations();
    }

    public Long getPlayer() {
        return player;
    }

    public int getTurn() {
        return turn;
    }

    public List<String> getLocations() {
        return Locations;
    }
}
