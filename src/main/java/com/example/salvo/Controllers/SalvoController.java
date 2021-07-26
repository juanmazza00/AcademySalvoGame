package com.example.salvo.Controllers;

import com.example.salvo.Models.*;
import com.example.salvo.Repositorys.*;

import com.example.salvo.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class SalvoController {



    @Autowired
    private GamePlayerRepository gp;
    @Autowired
    private PlayerRepository prepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ShipRepository srepo;
    @Autowired
    private SalvoRepository sal;


    @PostMapping("/games/players/{id}/salvoes")
    public ResponseEntity<Map<String, Object>> fireSalvoes(@PathVariable long id, @RequestBody Salvo salvoes, Authentication authentication) {
        Optional<GamePlayer> currentGp = gp.findById(id);
        Optional<Player> currentPlayer = prepo.findByUserName(authentication.getName());
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        if (authentication == null) {
            dto.put("error", "Must be logged in");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
        if (currentGp.isEmpty()) {
            dto.put("error", "Player doesn't exist");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);

        }
        if (currentPlayer.get().getGamePlayers().contains(currentGp)) {
            dto.put("error", "Can't join game");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
        Game game = currentGp.get().getGame();
        GamePlayer gamePlayer2;
        var gOp = gp.findAll().stream().filter(g -> g.getGame().equals(game) && g.getGamePlayerid() != currentGp.get().getGamePlayerid()).findFirst();
        if (gOp.isEmpty()) {
            dto.put("error", "You don't have an opponent");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }


        Set<Salvo> salvo1 = currentGp.get().getSalvoes();
        Set<Salvo> salvo2 = gOp.get().getSalvoes();

        var turn = 1;


        if (currentGp.get().getSalvoes().size() >= 1) {
            System.out.println(  turn =  currentGp.get().getSalvoes().size());
            turn =  currentGp.get().getSalvoes().size() + 1 ;
        }
        if (salvo1.size() > salvo2.size()) {
            dto.put("error", "It isn't your turn to play");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }
        if (salvoes.getSalvoLocations().size() <= 0) {
            dto.put("error", "You must at least shoot once");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }
        if (salvoes.getSalvoLocations().size() > 5) {
            dto.put("error", "You have a maximum of 5 shoots");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }

        

        currentGp.get().addSalvo(salvoes);
        sal.save(new Salvo(turn, salvoes.getSalvoLocations(), currentGp.get()));


        return new ResponseEntity<>(util.makeMap("OK", "Salvoes fired!"), HttpStatus.CREATED);
    }


}



