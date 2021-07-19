package com.example.salvo.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long salvoid;
    private int turn;

    @ElementCollection
    @Column(name = "SalvoLocation")
    private List<String> SalvoLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer")
    private GamePlayer gamePlayer;

    public Salvo() {
    }

    public Salvo(int turn, List<String> salvoLocation, GamePlayer gamePlayer) {
        this.turn = turn;
        SalvoLocation = salvoLocation;
        this.gamePlayer = gamePlayer;
    }

    public Long getSalvoid() {
        return salvoid;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getSalvoLocation() {
        return SalvoLocation;
    }

    public void setSalvoLocation(List<String> salvoLocation) {
        SalvoLocation = salvoLocation;
    }
}