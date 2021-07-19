package com.example.salvo.dtos;

import com.example.salvo.Models.Player;

public class PlayerDto {
    private long id;
    private String email;


    public PlayerDto(Player player) {
        this.id = player.getUserid();
        this.email = player.getUserName();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
