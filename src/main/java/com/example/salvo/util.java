package com.example.salvo;

import com.example.salvo.Models.GamePlayer;
import com.example.salvo.Models.Player;
import com.example.salvo.Repositorys.GamePlayerRepository;
import com.example.salvo.Repositorys.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class util {

    @Autowired
    private  PlayerRepository prepo;
    @Autowired
    private  GamePlayerRepository gp;





//    public static Player currentPlayer(Authentication authentication){
//        return prepo.findByUserName(authentication.getName());
//    }

    public static boolean isGuest(Authentication authentication){
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public static Map<String, Object> makeMap(String key, Object value){
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
