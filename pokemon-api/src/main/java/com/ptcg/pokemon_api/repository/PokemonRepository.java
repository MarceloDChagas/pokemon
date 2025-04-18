package com.ptcg.pokemon_api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ptcg.pokemon_api.model.Pokemon;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {
    Pokemon findByName(String name);
    List<Pokemon> findByType(String type);
    List<Pokemon> findByCardType(String cardType);
    List<Pokemon> findByRarity(String rarity);
    List<Pokemon> findByEvolutionStage(String evolutionStage);
}
