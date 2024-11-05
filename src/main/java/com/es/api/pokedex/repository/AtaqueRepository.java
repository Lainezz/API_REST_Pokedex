package com.es.api.pokedex.repository;

import com.es.api.pokedex.model.Ataque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtaqueRepository extends JpaRepository<Ataque, String> {
}
