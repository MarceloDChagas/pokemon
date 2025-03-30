package com.ptcg.pokemon_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.model.Enum.*;
import com.ptcg.pokemon_api.model.valueObject.*;

@Document(collection = "pokemons")
public class Pokemon {
    
  @Id
    private String id;

    @Field("name")
    @JsonProperty("name")
    private Name name;

    @Field("type")
    private PokemonType type;

    @Field("description")
    private Description description;

    @Field("cardType")
    private CardType cardType;

    @Field("rarity")
    private PokemonRarity rarity;

    @Field("evolutionStage")
    private EvolutionStage evolutionStage;

    @Field("pokemonStatus")
    private PokemonStatus pokemonStatus;
    
    public Pokemon() {
    }

    // Construtor para desserialização
    @JsonCreator
    public Pokemon(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") PokemonType type,
        @JsonProperty("pokemonStatus") PokemonStatus pokemonStatus
    ) {
        this.id = id;
        this.name = new Name(name);
        this.type = type;
        this.pokemonStatus = pokemonStatus;
    }

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

    public String getFormattedName() {
        return name != null ? name.getName() : null;
    }
}
