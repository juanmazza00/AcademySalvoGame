package com.example.salvo.dtos;


import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Salvo;
import com.example.salvo.Models.Ship;
import com.example.salvo.util;


import java.util.*;
import java.util.stream.Collectors;

public class HitDto {
    private List<Map<String, Object>> self;
    private List<Map<String, Object>> opponent;


    public HitDto(GamePlayer gamePlayer) {

        List<Salvo> selfa = util.fromSetToList(gamePlayer.getSalvoes());
        this.opponent = selfa.stream().map(this::getHits).collect(Collectors.toList());
        this.self = new ArrayList<>();
        if (util.opponent(gamePlayer).isPresent()) {
            List<Salvo> oppa = util.fromSetToList(util.opponent(gamePlayer).get().getSalvoes());
            this.self = oppa.stream().map(this::getHits).collect(Collectors.toList());
        }
    }

    private Map<String, Object> getHits(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap();
        dto.put("turn", salvo.getTurn());
        dto.put("hitLocations", this.getHitLocations(salvo));
        dto.put("damages", this.getDamages(salvo));
        ;
        dto.put("missed", salvo.getSalvoLocations().size() - getHitLocations(salvo).size());
        return dto;
    }


    private List getHitLocations(Salvo salvo) {
        if (util.opponent(salvo.getGamePlayer()).isEmpty()) {
            return null;
        }
        var ships = util.opponent(salvo.getGamePlayer()).get().getShips().stream().flatMap(s -> s.getShipLocations().stream()).collect(Collectors.toList());
        return salvo.getSalvoLocations().stream().filter(ships::contains).collect(Collectors.toList());
    }

    private Object getMissed(Salvo salvo) {
        if (util.opponent(salvo.getGamePlayer()).isEmpty()) {
            return null;
        }
        var ships = util.opponent(salvo.getGamePlayer()).get().getShips().stream().flatMap(s -> s.getShipLocations().stream()).collect(Collectors.toList());
        return salvo.getSalvoLocations().stream().filter(s -> !(ships.contains(s))).collect(Collectors.toList());
    }

    private Map<String, Object> getDamages(Salvo salvo) {
        if (util.opponent(salvo.getGamePlayer()).isEmpty()) {
            return null;
        }
        GamePlayer opponent = util.opponent(salvo.getGamePlayer()).get();

        Map<String, Object> dto = new LinkedHashMap<>();

        List<String> carrierLocations = new ArrayList<>();
        List<String> battleshipLocations = new ArrayList<>();
        List<String> submarineLocations = new ArrayList<>();
        List<String> destroyerLocations = new ArrayList<>();
        List<String> patrolboatLocations = new ArrayList<>();


        for (Ship ship : util.opponent(salvo.getGamePlayer()).get().getShips()) {
            switch (ship.getType()) {
                case "carrier":
                    carrierLocations = ship.getShipLocations();
                    break;
                case "battleship":
                    battleshipLocations = ship.getShipLocations();
                    break;
                case "submarine":
                    submarineLocations = ship.getShipLocations();
                    break;
                case "destroyer":
                    destroyerLocations = ship.getShipLocations();
                    break;
                case "patrolboat":
                    patrolboatLocations = ship.getShipLocations();
            }

        }

        List<String> allSalvoes = salvo.getGamePlayer().getSalvoes().stream().filter(s -> s.getTurn() <= salvo.getTurn()).flatMap(h -> h.getSalvoLocations().stream()).collect(Collectors.toList());


        dto.put("carrierHits", carrierLocations.stream().filter(d -> salvo.getSalvoLocations().contains(d)).count());
        dto.put("battleshipHits", battleshipLocations.stream().filter(d -> salvo.getSalvoLocations().contains(d)).count());
        dto.put("submarineHits", submarineLocations.stream().filter(d -> salvo.getSalvoLocations().contains(d)).count());
        dto.put("destroyerHits", destroyerLocations.stream().filter(d -> salvo.getSalvoLocations().contains(d)).count());
        dto.put("patrolboatHits", patrolboatLocations.stream().filter(d -> salvo.getSalvoLocations().contains(d)).count());
        dto.put("carrier", carrierLocations.stream().filter(allSalvoes::contains).count());
        dto.put("battleship", battleshipLocations.stream().filter(allSalvoes::contains).count());
        dto.put("submarine", submarineLocations.stream().filter(allSalvoes::contains).count());
        dto.put("destroyer", destroyerLocations.stream().filter(allSalvoes::contains).count());
        dto.put("patrolboat", patrolboatLocations.stream().filter(allSalvoes::contains).count());
        return dto;
    }


    ////-----Setters y getters-----//
    public List<Map<String, Object>> getSelf() {
        return self;
    }


    public void setSelf(List<Map<String, Object>> self) {
        this.self = self;
    }

    public List<Map<String, Object>> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<Map<String, Object>> opponent) {
        this.opponent = opponent;
    }
}
