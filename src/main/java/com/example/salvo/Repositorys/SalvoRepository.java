package com.example.salvo.Repositorys;

import com.example.salvo.Models.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;




@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo,Long> {}