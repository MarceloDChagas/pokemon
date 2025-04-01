package com.ptcg.pokemon_api.model.valueObject;

import java.util.Date;

public class CollectionItem {
    private String pokemonId;
    private Date addedAt;
    private int quantity;
    
    public CollectionItem() {
        this.addedAt = new Date();
        this.quantity = 1;
    }
    
    public CollectionItem(String pokemonId) {
        this.pokemonId = pokemonId;
        this.addedAt = new Date();
        this.quantity = 1;
    }
    
    public CollectionItem(String pokemonId, int quantity) {
        this.pokemonId = pokemonId;
        this.addedAt = new Date();
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public String getPokemonId() {
        return pokemonId;
    }
    
    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }
    
    public Date getAddedAt() {
        return addedAt;
    }
    
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void incrementQuantity() {
        this.quantity++;
    }
    
    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}
