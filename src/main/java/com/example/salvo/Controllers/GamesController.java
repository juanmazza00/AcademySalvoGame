package com.example.salvo.Controllers;

import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.GameRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import com.example.salvo.dtos.GameDto;
import com.example.salvo.dtos.PlayerDto;
import com.example.salvo.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class GamesController {

    @Autowired
    private GameRepository repo;
    @Autowired
    private GamePlayerRepository gp;
    @Autowired
    private PlayerRepository prepo;


    @PostMapping("/games")
    public ResponseEntity<Map<String, Object>> CreateGame(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Optional<Player> currentPlayer = prepo.findByUserName(authentication.getName());

        if (util.isGuest(authentication)) {
            dto.put("error", "Guests can't create a games");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
        Game game = repo.save(new Game(new Date()));
        GamePlayer gamePlayer = gp.save(new GamePlayer(game, currentPlayer.get(), new Date()));
        dto.put("gpid", gamePlayer.getGamePlayerid());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }
    @GetMapping("/games")

    public Map<String, Object> getAll (Authentication authentication){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (util.isGuest(authentication)) {
            dto.put("player", "Guest");

        } else {
            var paut = prepo.findByUserName(authentication.getName());
            dto.put("player", new PlayerDto(paut.get()));


        }
        dto.put("games", repo.findAll().stream().map(g -> new GameDto(g)).collect(Collectors.toList()));
        return dto;
    }
}
