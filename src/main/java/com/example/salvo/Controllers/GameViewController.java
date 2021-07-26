package com.example.salvo.Controllers;

import com.example.salvo.GameState;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Models.Score;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import com.example.salvo.Repositorys.ScoreRepository;
import com.example.salvo.dtos.*;
import com.example.salvo.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class GameViewController {

    @Autowired
    private GamePlayerRepository gp;
    @Autowired
    private PlayerRepository prepo;
    @Autowired
    private ScoreRepository Screpo;


    @GetMapping("/game_view/{gamePlayerid}")
    public ResponseEntity<Map<String, Object>> getGameView (
            @PathVariable long gamePlayerid, Authentication authentication){
        GamePlayer gamePlayer = gp.getOne(gamePlayerid);
        Optional<Player> currentPlayer = prepo.findByUserName(authentication.getName());

        if (currentPlayer.get().getGamePlayers().contains(gamePlayer)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dto = mapper.convertValue(new GameViewDto(gamePlayer), Map.class);
            if (dto.containsValue("WIN")){
                Screpo.save (new Score(1.0, new Date(), currentPlayer.get(),  gamePlayer.getGame())) ;
            }
            if (dto.containsValue("LOSE")){
                Screpo.save (new Score(0.0, new Date(), currentPlayer.get(),  gamePlayer.getGame())) ;
            }
            if (dto.containsValue("TIE")){
                Screpo.save (new Score(0.5, new Date(), currentPlayer.get(),  gamePlayer.getGame())) ;
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            Map<String, Object> dto = new LinkedHashMap<String, Object>();
            dto.put("error", "Cant view this game");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }


    }

}
