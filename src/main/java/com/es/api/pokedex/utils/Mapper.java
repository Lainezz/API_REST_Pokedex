package com.es.api.pokedex.utils;

import com.es.api.pokedex.dto.AtaqueDTO;
import com.es.api.pokedex.dto.PokemonDTO;
import com.es.api.pokedex.dto.TipoDTO;
import com.es.api.pokedex.model.Ataque;
import com.es.api.pokedex.model.Pokemon;
import com.es.api.pokedex.model.Tipo;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static PokemonDTO entityToDTO(Pokemon p) {
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

    public static Pokemon DTOToEntity(PokemonDTO pDto) {
        Pokemon p = new Pokemon();
        p.setNombre(pDto.getNombre());
        p.setVida(pDto.getVida());

        Tipo tipoAtaque = new Tipo();
        tipoAtaque.setTipo(pDto.getAtaque().getTipo().getNombre());

        Ataque a = new Ataque();
        a.setNombre(pDto.getAtaque().getNombre());
        a.setEspecial(pDto.getAtaque().isEspecial());
        a.setDanioBase(pDto.getAtaque().getDanioBase());
        a.setTipo(tipoAtaque);

        List<Ataque> ataques = new ArrayList<>();
        ataques.add(a);
        p.setAtaques(ataques);

        Tipo tipoPokemon = new Tipo();
        tipoPokemon.setTipo(pDto.getTipo().getNombre());

        p.setTipo(tipoPokemon);

        return p;

    }




}
