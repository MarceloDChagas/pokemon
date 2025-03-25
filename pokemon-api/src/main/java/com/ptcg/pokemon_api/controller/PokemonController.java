package com.ptcg.pokemon_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {

    @GetMapping("/pokemon")
    public String getPokemon() {
        return "Hello, Pokemon!";
    }
}
