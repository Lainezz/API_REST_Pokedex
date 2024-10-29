package com.es.api.pokedex.service;

import com.es.api.pokedex.dto.AtaqueDTO;
import com.es.api.pokedex.dto.PokemonDTO;
import com.es.api.pokedex.dto.TipoDTO;
import com.es.api.pokedex.model.Pokemon;
import com.es.api.pokedex.repository.PokemonRepositoryApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepositoryApi repository;

    public PokemonDTO getById(String id) {

        Long idEntity = Long.parseLong(id);

        Pokemon p = repository.findById(idEntity).orElse(null);

        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setNombre(p.getNombre());
        pokemonDTO.setVida(p.getVida());

        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre(p.getTipo().getTipo());
        pokemonDTO.setTipo(tipoDTO);

        AtaqueDTO ataqueDTO = new AtaqueDTO();
        TipoDTO tipoAtaqueDTO = new TipoDTO();
        tipoAtaqueDTO.setNombre(p.getAtaques().get(0).getTipo().getTipo());
        ataqueDTO.setNombre(p.getAtaques().get(0).getNombre());
        ataqueDTO.setEspecial(p.getAtaques().get(0).isEspecial());
        ataqueDTO.setDanioBase(p.getAtaques().get(0).getDanioBase());
        ataqueDTO.setTipo(tipoAtaqueDTO);
        pokemonDTO.setAtaque(ataqueDTO);

        return pokemonDTO;
    }

    public List<PokemonDTO> getAll() {

        List<PokemonDTO> listaDeDTOs = new ArrayList<>();

        List<Pokemon> listaPok = repository.findAll();

        for (Pokemon p: listaPok) {
            PokemonDTO pokemonDTO = new PokemonDTO();
            pokemonDTO.setNombre(p.getNombre());
            pokemonDTO.setVida(p.getVida());

            TipoDTO tipoDTO = new TipoDTO();
            tipoDTO.setNombre(p.getTipo().getTipo());
            pokemonDTO.setTipo(tipoDTO);

            AtaqueDTO ataqueDTO = new AtaqueDTO();
            TipoDTO tipoAtaqueDTO = new TipoDTO();
            tipoAtaqueDTO.setNombre(p.getAtaques().get(0).getTipo().getTipo());
            ataqueDTO.setNombre(p.getAtaques().get(0).getNombre());
            ataqueDTO.setEspecial(p.getAtaques().get(0).isEspecial());
            ataqueDTO.setDanioBase(p.getAtaques().get(0).getDanioBase());
            ataqueDTO.setTipo(tipoAtaqueDTO);
            pokemonDTO.setAtaque(ataqueDTO);

            listaDeDTOs.add(pokemonDTO);
        }

        return listaDeDTOs;
    }

    public PokemonDTO insert(PokemonDTO pokemonDTO) {
        return null;
    }

    public PokemonDTO update(PokemonDTO pokemonDTO, String id) {
        return null;
    }
}
