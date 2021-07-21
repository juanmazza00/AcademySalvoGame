package com.example.salvo.dtos;



import java.util.ArrayList;
import java.util.List;

public class HitDto {
    private List<String> self;
    private List<String> opponent;


    public HitDto() {
        this.self = new ArrayList<>();
        this.opponent = new ArrayList<>();
    }



    public List<String> getSelf() {
        return self;
    }

    public void setSelf(List<String> self) {
        this.self = self;
    }

    public List<String> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<String> opponent) {
        this.opponent = opponent;
    }
}
