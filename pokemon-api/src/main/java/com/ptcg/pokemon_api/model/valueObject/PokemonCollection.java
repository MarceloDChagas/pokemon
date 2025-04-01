package com.ptcg.pokemon_api.model.valueObject;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PokemonCollection {
    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    private Date createdAt;
    
    private List<CollectionItem> items = new ArrayList<>();
    
    public PokemonCollection() {
        this.createdAt = new Date();
    }
    
    public PokemonCollection(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = new Date();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<CollectionItem> getItems() {
        return items;
    }
    
    public void setItems(List<CollectionItem> items) {
        this.items = items;
    }
    
    public void addItem(CollectionItem item) {
        this.items.add(item);
    }
    
    public void removeItem(String pokemonId) {
        this.items.removeIf(item -> item.getPokemonId().equals(pokemonId));
    }
}