package com.ptcg.pokemon_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ptcg.pokemon_api.model.valueobject.PokemonCollection;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    @Field("password")
    private String password; 
    
    @Field("email")
    private String email;
    
    private List<PokemonCollection> collections = new ArrayList<>();
    
    public User() {
    }
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<PokemonCollection> getCollections() {
        return collections;
    }
    
    public void setCollections(List<PokemonCollection> collections) {
        this.collections = collections;
    }
    
    public void addCollection(PokemonCollection collection) {
        this.collections.add(collection);
    }
    
    public void removeCollection(String collectionId) {
        this.collections.removeIf(collection -> collection.getId().equals(collectionId));
    }

    public void updateCollection(PokemonCollection updatedCollection) {
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).getId().equals(updatedCollection.getId())) {
                collections.set(i, updatedCollection);
                return;
            }
        }
        throw new IllegalArgumentException("Collection not found for update");
    }
    
}