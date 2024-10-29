package com.es.api.pokedex.controller;

import com.es.api.pokedex.dto.PokemonDTO;
import com.es.api.pokedex.model.Pokemon;
import com.es.api.pokedex.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemon") // URI hasta este punto es: localhost:8080/pokemon
public class PokemonController {

    @Autowired
    private PokemonService service;

    @GetMapping("/{id}") // URI -> GET localhost:8080/pokemon/{id}
    public PokemonDTO getById(
            @PathVariable String id
    ) {
        return service.getById(id);
    }

    @GetMapping("/") // URI -> GET localhost:8080/pokemon/
    public List<PokemonDTO> getAll() {
        return null;
    }

    @PostMapping("/") // URI -> POST localhost:8080/pokemon/
    public PokemonDTO insert(
            @RequestBody PokemonDTO pokemonDTO
    ) {
        return null;
    }

    @PutMapping("/{id}") // URI -> PUT localhost:8080/pokemon/{id}
    public PokemonDTO update(
            @RequestBody PokemonDTO pokemonDTO,
            @PathVariable String id
    ) {
        return null;
    }

}
