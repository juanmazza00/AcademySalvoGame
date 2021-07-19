package com.example.salvo.dtos;

import com.example.salvo.Models.Ship;

import java.util.List;

public class ShipDto {
    private String type;
    private List<String> locations;

    public ShipDto(Ship ship) {
        this.type = ship.getType();
        this.locations = ship.getShipLocations();
    }


    public String getType() {
        return type;
    }

    public List<String> getLocations() {
        return locations;
    }
}
