package com.example.salvo.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long userid;
    private String userName;
    private String password;



    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> score = new HashSet<>();



    public Player() {
    }


    public Player(String user, String pass){
        userName = user;
        password = pass;
    }


    public Optional<Score> getScore(Game game) {
        return this.score.stream().filter(s -> s.getGame().equals(game) ).findFirst();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }


    public long getUserid() {
        return userid;
    }

   // public Score getScore(Game game){
     //  game.getScore().stream().filter(score ->);


    //}


}

