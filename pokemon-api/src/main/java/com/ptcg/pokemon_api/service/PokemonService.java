package com.ptcg.pokemon_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptcg.pokemon_api.repository.PokemonRepository;
import com.ptcg.pokemon_api.model.Pokemon;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    public Pokemon savePokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public Pokemon getPokemonById(String id) {
        return pokemonRepository.findById(id).orElse(null);
    }

    public Pokemon getPokemonByName(String name) {
        return pokemonRepository.findByName(name);
    }

    public List<Pokemon> getPokemonByType(String type) {
        return pokemonRepository.findByType(type);
    }

    public List<Pokemon> getAllPokemons() {
        return pokemonRepository.findAll();
    }

    public List<Pokemon> getPokemonByCardType(String cardType) {
        return pokemonRepository.findByCardType(cardType);
    }

    public List<Pokemon> getPokemonByRarity(String rarity) {
        return pokemonRepository.findByRarity(rarity);
    }

    public List<Pokemon> getPokemonByEvolutionStage(String evolutionStage) {
        return pokemonRepository.findByEvolutionStage(evolutionStage);
    }
}