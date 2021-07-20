package com.example.salvo.Controllers;

import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Models.Salvo;
import com.example.salvo.Repositorys.*;
import com.example.salvo.Models.Ship;
import com.example.salvo.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {


    @Autowired
    private GameRepository repo;
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


    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(

            @RequestParam String email, @RequestParam String password) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (email.isEmpty() || password.isEmpty()) {
            dto.put("error", "Blank spaces found");
            return new ResponseEntity(dto, HttpStatus.FORBIDDEN);
        }

        if (prepo.findByUserName(email) != null) {
            dto.put("error", "Name already in use");
            return new ResponseEntity(dto, HttpStatus.FORBIDDEN);
        }


        prepo.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/game/{id}/players")
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long id, Authentication authentication) {
        Optional<Game> game = repo.findById(id);
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

        GamePlayer gamePlayer = new GamePlayer(game.get(), currentPlayer(authentication), new Date());
        gp.save(gamePlayer);
        dto.put("gpid", gamePlayer.getGamePlayerid());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/games")
    public ResponseEntity<Map<String, Object>> CreateGame(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        if (isGuest(authentication)) {
            dto.put("error", "Guests can't create a games");
            return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
        }
        Game game = repo.save(new Game(new Date()));
        GamePlayer gamePlayer = gp.save(new GamePlayer(game, currentPlayer(authentication), new Date()));
        dto.put("gpid", gamePlayer.getGamePlayerid());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }


            @PostMapping("/games/players/{id}/ships")
        public ResponseEntity<Map<String, Object>> placeShips(@PathVariable long id, @RequestBody List<Ship> ships, Authentication authentication) {

            Optional<GamePlayer> currentGp = gp.findById(id);
            Map<String, Object> dto = new LinkedHashMap<String, Object>();

            if (authentication == null) {
                dto.put("error", "Must be logged in");
                return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
            }
            if (currentGp.isEmpty()) {
                dto.put("error", "Player doesn't exist");
                return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
            }
            if (currentPlayer(authentication).getGamePlayers().contains(currentGp)) {
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
            return new ResponseEntity<>(makeMap("OK","Ships placed!"), HttpStatus.CREATED);}

        @PostMapping("/games/players/{id}/salvoes")
        public ResponseEntity<Map<String, Object>> fireSalvoes(@PathVariable long id, @RequestBody Salvo salvoes, Authentication authentication) {
            Optional<GamePlayer> currentGp = gp.findById(id);
            Map<String, Object> dto = new LinkedHashMap<String, Object>();

            if (authentication == null) {
                dto.put("error", "Must be logged in");
                return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
            }
            if (currentGp.isEmpty()) {
                dto.put("error", "Player doesn't exist");
                return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);

            }
            if (currentPlayer(authentication).getGamePlayers().contains(currentGp)) {
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

            var turn = 0;


            if (currentGp.get().getSalvoes().size() >= 1) {
                turn = currentGp.get().getSalvoes().size();

            }

            if (salvo1.size() > salvo2.size()) {
                dto.put("error", "It isn't your turn to play");
              return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }
            if (currentGp.get().getSalvoes().size() >= 1){
                turn = currentGp.get().getSalvoes().size();


            }
            if (salvoes.getSalvoLocations().size() <= 0 ) {
                dto.put("error", "You must at least shoot once");
                return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
            }
            if (salvoes.getSalvoLocations().size() > 5) {
                dto.put("error", "You have a maximum of 5 shoots");
                return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
            }

                currentGp.get().addSalvo(salvoes);
                sal.save(new Salvo( turn, salvoes.getSalvoLocations(), currentGp.get()));


            return new ResponseEntity<>(makeMap("OK","Salvoes fired!"), HttpStatus.CREATED);}

    @GetMapping("/games")

                        public Map<String, Object> getAll (Authentication authentication){
                            Map<String, Object> dto = new LinkedHashMap<String, Object>();
                            if (isGuest(authentication)) {
                                dto.put("player", "Guest");

                            } else {
                                var paut = prepo.findByUserName(authentication.getName());
                                dto.put("player", new PlayerDto(paut));


                            }
                            dto.put("games", repo.findAll().stream().map(g -> new GameDto(g)).collect(Collectors.toList()));
                            return dto;
                        }

//

                        @GetMapping("/game_view/{gamePlayerid}")
                        public ResponseEntity<Map<String, Object>> getGameView (
                        @PathVariable long gamePlayerid, Authentication authentication){
                            GamePlayer gamePlayer = gp.getById(gamePlayerid);
                            Map<String, Object> dto = new LinkedHashMap<String, Object>();
                            if (currentPlayer(authentication).getGamePlayers().contains(gamePlayer)) {
                                dto.put("gameState", "PLACESHIPS");
                                dto.put("id", gamePlayer.getGame().getGameid());
                                dto.put("created", gamePlayer.getDate());
                                dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(gmp -> new GamePlayerDto(gmp)).collect(Collectors.toList()));
                                dto.put("ships", gamePlayer.getShips().stream().map(ShipDto::new).collect(Collectors.toList()));
                                dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream().flatMap(sv -> sv.getSalvoes().stream().map(sp -> new SalvoDto(sp))).collect(Collectors.toList()));
                                dto.put("hits", makeHitDTO());
                                return new ResponseEntity<>(dto, HttpStatus.OK);
                            } else {
                                dto.put("error", "Cant view this game");
                                return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
                            }


                        }


                        private Map<String, Object> makeHitDTO () {
                            Map<String, Object> dto = new LinkedHashMap<String, Object>();
                            List<String> selfHitLocations = new ArrayList<>();
                            List<String> opponentHitLocations = new ArrayList<>();
                            dto.put("self", selfHitLocations);
                            dto.put("opponent", opponentHitLocations);
                            return dto;
                        }
//


                        public Player currentPlayer (Authentication authentication){
                            return prepo.findByUserName(authentication.getName());
                        }

                        private boolean isGuest (Authentication authentication){
                            return authentication == null || authentication instanceof AnonymousAuthenticationToken;
                        }

                        private Map<String, Object> makeMap (String key, Object value){
                            Map<String, Object> map = new HashMap<>();
                            map.put(key, value);
                            return map;
                        }


                    }




