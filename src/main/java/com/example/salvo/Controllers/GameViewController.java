package com.example.salvo.Controllers;

import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.PlayerRepository;
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


    @GetMapping("/game_view/{gamePlayerid}")
    public ResponseEntity<Map<String, Object>> getGameView (
            @PathVariable long gamePlayerid, Authentication authentication){
        GamePlayer gamePlayer = gp.getById(gamePlayerid);
        Optional<Player> currentPlayer = prepo.findByUserName(authentication.getName());

        if (currentPlayer.get().getGamePlayers().contains(gamePlayer)) {
//            dto.put("gameState", "PLACESHIPS");
//            dto.put("id", gamePlayer.getGame().getGameid());
//            dto.put("created", gamePlayer.getDate());
//            dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(gmp -> new GamePlayerDto(gmp)).collect(Collectors.toList()));
//            dto.put("ships", gamePlayer.getShips().stream().map(ShipDto::new).collect(Collectors.toList()));
//            dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream().flatMap(sv -> sv.getSalvoes().stream().map(sp -> new SalvoDto(sp))).collect(Collectors.toList()));
//            dto.put("hits", new HitDto());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dto = mapper.convertValue(new GameViewDto(gamePlayer), Map.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            Map<String, Object> dto = new LinkedHashMap<String, Object>();
            dto.put("error", "Cant view this game");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
    }

     private Map<String, Object> MakeHitDto(){
         Map<String, Object> dto = new LinkedHashMap() ;
            dto.put("self", new ArrayList<>());
            dto.put("opponent", new ArrayList<>());
            return dto;
    }
}
