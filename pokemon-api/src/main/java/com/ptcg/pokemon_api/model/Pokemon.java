package com.ptcg.pokemon_api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.ptcg.pokemon_api.model.Enum.*;
import com.ptcg.pokemon_api.model.valueobject.*;

@Document(collection = "pokemons")
public class Pokemon {

    @Id
    private final String id;

    @Field("name")
    private final Name name;

    @Field("type")
    private final PokemonType type;

    @Field("description")
    private final Description description;

    @Field("cardType")
    private final CardType cardType;

    @Field("rarity")
    private final PokemonRarity rarity;

    @Field("evolutionStage")
    private final EvolutionStage evolutionStage;

    @Field("pokemonStatus")
    private final PokemonStatus pokemonStatus;

    private Pokemon(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.description = builder.description;
        this.cardType = builder.cardType;
        this.rarity = builder.rarity;
        this.evolutionStage = builder.evolutionStage;
        this.pokemonStatus = builder.pokemonStatus;
    }

    public static class Builder {
        private String id;
        private Name name;
        private PokemonType type;
        private Description description;
        private CardType cardType;
        private PokemonRarity rarity;
        private EvolutionStage evolutionStage;
        private PokemonStatus pokemonStatus;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder type(PokemonType type) {
            this.type = type;
            return this;
        }

        public Builder description(Description description) {
            this.description = description;
            return this;
        }

        public Builder cardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        public Builder rarity(PokemonRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Builder evolutionStage(EvolutionStage evolutionStage) {
            this.evolutionStage = evolutionStage;
            return this;
        }

        public Builder pokemonStatus(PokemonStatus pokemonStatus) {
            this.pokemonStatus = pokemonStatus;
            return this;
        }

        public Pokemon build() {
            return new Pokemon(this);
        }
    }

    public boolean hasSameName(Name other) {
        return name.isSame(other);
    }

    public boolean hasType(PokemonType type) {
        return this.type == type;
    }

    public boolean hasAttackNamed(String name) {
        return pokemonStatus.hasAttackNamed(name);
    }

    public int getAttackDamageByName(String name) {
        return pokemonStatus.getDamageByName(name);
    }

    public int getHp() {
        return pokemonStatus.getHp();
    }

    public List<Attack> getAttacks() {
        return pokemonStatus.getAttacks();
    }

    public int getAttackCount() {
        return pokemonStatus.getAttackCount();
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public PokemonType getType() {
        return type;
    }

    public Description getDescription() {
        return description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public PokemonRarity getRarity() {
        return rarity;
    }

    public EvolutionStage getEvolutionStage() {
        return evolutionStage;
    }

    public PokemonStatus getPokemonStatus() {
        return pokemonStatus;
    }
}