package com.example.salvo;

import com.example.salvo.Models.Game;
import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Models.Salvo;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;

public class util {

    @Autowired
    private  PlayerRepository prepo;
    @Autowired
    private  GamePlayerRepository gp;



    public static boolean isGuest(Authentication authentication){
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public static Map<String, Object> makeMap(String key, Object value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


   public static Optional<GamePlayer> opponent (GamePlayer gamePlayer){
        return gamePlayer.getGame().getGamePlayers().stream().filter(g -> g.getGamePlayerid() != gamePlayer.getGamePlayerid()).findFirst();
   }
    public static List<Salvo> fromSetToList(Set<Salvo> salvos) {
        List<Salvo> aux = new ArrayList<>();
        for (int i = 1; i <= salvos.size(); i++) {
            int finalI = i;
            var salvo = salvos.stream().filter(s -> s.getTurn() == finalI).findFirst();
            if (salvo.isPresent()) {
                aux.add(salvo.get());
            }
        }
        return aux;
    }





}
