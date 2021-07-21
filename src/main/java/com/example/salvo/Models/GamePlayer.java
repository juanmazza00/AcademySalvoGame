package com.example.salvo.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long gamePlayerid;
    private Date date;
    private String userName;
    private int Hit;


    public GamePlayer() {
    }

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "salvoid")
//    private Salvo salvo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameid")
    private Game game;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "shipid")
//    private Ship ship;


    public GamePlayer(Player currentPlayer, Game game, LocalDateTime now) {
    }


    public long getGamePlayerid() {
        return gamePlayerid;
    }

    public GamePlayer(long gamePlayerid) {
        this.gamePlayerid = gamePlayerid;
    }


    public GamePlayer(Game game, Player player, Date date) {
        this.date = date;
        this.player = player;
        this.game = game;

    }


    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();

    public Set<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Salvo> SalvoLocation = new HashSet<>();

    public Set<Salvo> getSalvoes() {
        return SalvoLocation;
    }

    public void addSalvo(Salvo salvo) {
        SalvoLocation.add(salvo);
    }

    public Optional<Score> getScore(){
        return player.getScore(game);
    }



}















