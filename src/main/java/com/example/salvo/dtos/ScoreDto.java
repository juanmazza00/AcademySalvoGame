package com.example.salvo.dtos;

import com.example.salvo.Models.GamePlayer;

import java.util.Date;

public class ScoreDto {
    private long player;
    private Double score ;
    private Date finishDate;



    public ScoreDto(GamePlayer gp) {
        var gps = gp.getScore();
        if (gps.isEmpty()){
            this.player = 0;
            this.score = 0.0;
            this.finishDate = null;
        }else {
            this.player = gps.get().getPlayer().getUserid();
            this.score = gps.get().getScore();
            this.finishDate = gps.get().getFinishDate();
        }
    }

    public long getPlayer() {
        return player;
    }

    public Double getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
