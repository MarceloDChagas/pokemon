package com.ptcg.pokemon_api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ptcg.pokemon_api.exception.InvalidAttackException;
import com.ptcg.pokemon_api.exception.InvalidHpException;
import com.ptcg.pokemon_api.exception.PokemonNotFoundException;
import com.ptcg.pokemon_api.model.Pokemon;
import com.ptcg.pokemon_api.service.PokemonService;
import java.util.List;

@Validated // Habilita validações de parâmetros na URL
@RestController
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{id}")
    public Pokemon getPokemonById(
            @PathVariable String id) {
        Pokemon pokemon = pokemonService.getPokemonById(id);
        if (pokemon == null) {
            throw new PokemonNotFoundException("Pokémon com ID " + id + " não encontrado");
        }
        return pokemon;
    }

    @GetMapping("/type/{type}")
    public List<Pokemon> getPokemonByType(
            @PathVariable @NotBlank(message = "Tipo não pode estar vazio") String type) {
        List<Pokemon> pokemons = pokemonService.getPokemonByType(type);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("Nenhum Pokémon encontrado do tipo: " + type);
        }
        return pokemons;
    }

    @GetMapping("/pokemons")
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("Nenhum Pokémon cadastrado");
        }
        return pokemons;
    }

    @PostMapping("/create")
    public Pokemon createPokemon(@Valid @RequestBody Pokemon pokemon) {
        return pokemonService.savePokemon(pokemon);
    }
    
    @ExceptionHandler({InvalidAttackException.class, InvalidHpException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(Exception ex) {
        return ex.getMessage();
    }
}