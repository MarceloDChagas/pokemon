package com.ptcg.pokemon_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.service.PokemonService;

@RestController
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/pokemon")
    public String getPokemon() {
        return "Hello, Pokemon!";
    }

    @GetMapping("/pokemon/{id}")
    public Pokemon getPokemonById(@PathVariable String id) {
        Pokemon pokemon = pokemonService.getPokemonById(id);
        if (pokemon == null) {
            throw new RuntimeException("Pokemon not found with id: " + id); 
        }
        return pokemon;
    }

    @GetMapping("/pokemon/type/{type}")
    public List<Pokemon> getPokemonByType(@PathVariable String type) {
        List<Pokemon> pokemons = pokemonService.getPokemonByType(type);
        if (pokemons == null || pokemons.isEmpty()) {
            throw new RuntimeException("No Pokemon found with type: " + type);
        }
        return pokemons;
    }

    @GetMapping("/pokemon/all")
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        if (pokemons == null || pokemons.isEmpty()) {
            throw new RuntimeException("No Pokemon found in the database.");
        }
        return pokemons;
    }

    @PostMapping("/pokemon/create")
    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.savePokemon(pokemon);
    }
}