package com.example.salvo.Controllers;

import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Models.Ship;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import com.example.salvo.Repositorys.ShipRepository;
import com.example.salvo.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ShipController {
    @Autowired
    private GamePlayerRepository gp;
    @Autowired
    private ShipRepository srepo;
    @Autowired
    private PlayerRepository prepo;

    @PostMapping("/games/players/{id}/ships")
    public ResponseEntity<Map<String, Object>> placeShips(@PathVariable long id, @RequestBody List<Ship> ships, Authentication authentication) {

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
        if (currentGp.get().getShips().size() > 0) {
            dto.put("error", "Your ships have already been placed");
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
        }
        for (Ship ship : ships) {
            currentGp.get().addShip(ship);

            //              System.out.println(ship.getType()+" " +ship.getShipLocations()+" "+ currentGp.get());
            srepo.save(new Ship(ship.getType(), ship.getShipLocations(), currentGp.get()));


        }
        return new ResponseEntity<>(util.makeMap("OK","Ships placed!"), HttpStatus.CREATED);}
}
