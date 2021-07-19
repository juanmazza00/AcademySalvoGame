package com.example.salvo.Repositorys;

import com.example.salvo.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;




@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player,Long> {
     Player findByUserName(@Param("userName") String UserName);
}

