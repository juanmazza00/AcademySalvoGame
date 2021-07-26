package com.example.salvo.dtos;

import com.example.salvo.Models.Salvo;

import java.util.List;

public class SalvoDto {
    private int turn;
    private Long player;
    private List<String> Locations;


    public SalvoDto(Salvo salvo) {
        this.turn = salvo.getTurn();
        this.player = salvo.getGamePlayer().getPlayer().getUserid();
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
