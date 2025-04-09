package com.ptcg.pokemon_api.model.valueobject;

import java.util.Date;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class CollectionItem {

    @Id
    private final String pokemonId;
    
    @Field("addedAt")
    private final Date addedAt;

    @Field("quantity")
    private final Quantity quantity;

    private CollectionItem(Builder builder){
        this.addedAt = builder.addedAt;
        this.pokemonId = builder.pokemonId;
        this.quantity = builder.quantity;
    }
    public CollectionItem(String pokemonId, int quantity) {
        if (pokemonId == null || pokemonId.isBlank()) {
            throw new IllegalArgumentException("Pokemon ID cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.pokemonId = pokemonId;
        this.quantity = new Quantity(quantity);
        this.addedAt = new Date();
    }

    public static class Builder{
        private String pokemonId;
        private Date addedAt;
        private Quantity quantity;

        public Builder pokemonId(String pokemonId) {
            validatePokemonId(pokemonId);
            this.pokemonId = pokemonId;
            return this;
        }

        public Builder addedAt(Date addedAt) {
            this.addedAt = addedAt;
            return this;
        }

        public Builder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public CollectionItem build() {
            return new CollectionItem(this);
        }
    }
    
    private static void validatePokemonId(String pokemonId) {
        if (!isValidPokemonId(pokemonId)) {
            throw new IllegalArgumentException("Invalid Pokémon ID: " + pokemonId);
        }
    }

    private static boolean isValidPokemonId(String pokemonId) {
        return pokemonId != null && !pokemonId.trim().isEmpty();
    }

    public String getPokemonId() {
        return pokemonId;
    }

    public Date getAddedAt() {
        return new Date(addedAt.getTime());
    }

    public CollectionItem incrementQuantity() {
        return new Builder()
                .pokemonId(this.pokemonId)
                .addedAt(this.addedAt)
                .quantity(this.quantity.increment())
                .build();
    }
    
    public CollectionItem decrementQuantity() {
        return new Builder()
                .pokemonId(this.pokemonId)
                .addedAt(this.addedAt)
                .quantity(this.quantity.decrement())
                .build();
    }
    

    public Quantity getQuantity() {
        return quantity;
    }

    public CollectionItem withUpdatedQuantity(int newQuantity) {
        return new CollectionItem(this.pokemonId, newQuantity);
    }

    public static CollectionItem create(String pokemonId, int quantity) {
        return new CollectionItem(pokemonId, quantity);
    }

    @Override
    public String toString() {
        return "CollectionItem{" +
               "pokemonId='" + pokemonId + '\'' +
               ", addedAt=" + addedAt +
               ", quantity=" + quantity +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionItem)) return false;
        CollectionItem that = (CollectionItem) o;
        return Objects.equals(pokemonId, that.pokemonId) &&
               Objects.equals(addedAt, that.addedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pokemonId, addedAt);
    }
}
