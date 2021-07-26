package com.example.salvo.Controllers;

import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.GameRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import com.example.salvo.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PlayerController {
    @Autowired
    private GameRepository repo;
    @Autowired
    private GamePlayerRepository gp;
    @Autowired
    private PlayerRepository prepo;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(

            @RequestParam String email, @RequestParam String password) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (email.isEmpty() || password.isEmpty()) {
            dto.put("error", "Blank spaces found");
            return new ResponseEntity(dto, HttpStatus.FORBIDDEN);
        }

        if (prepo.findByUserName(email).isPresent()) {
            dto.put("error", "Name already in use");
            return new ResponseEntity(dto, HttpStatus.FORBIDDEN);
        }


        prepo.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/game/{id}/players")
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long id, Authentication authentication) {
        Optional<Game> game = repo.findById(id);
        Optional<Player> currentPlayer = prepo.findByUserName(authentication.getName());
        Map<String, Object> dto = new LinkedHashMap<>();

        if (authentication == null) {
            dto.put("error", "Cannot join game");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
        if (game.isEmpty()) {
            dto.put("error", "Game does not exist");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }
        if (game.get().getGamePlayers().size() >= 2) {
            dto.put("error", "Game is full");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }

        GamePlayer gamePlayer = new GamePlayer(game.get(), currentPlayer.get(), new Date());
        gp.save(gamePlayer);
        dto.put("gpid", gamePlayer.getGamePlayerid());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}

