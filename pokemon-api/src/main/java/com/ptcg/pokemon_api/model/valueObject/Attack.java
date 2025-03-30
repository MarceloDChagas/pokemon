package com.ptcg.pokemon_api.model.valueObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.exception.InvalidAttackException;

public class Attack {
    @JsonProperty("name") 
    private String name;
    
    @JsonProperty("damage") 
    private int damage;

   @JsonCreator
    public Attack(
        @JsonProperty("name") String name,
        @JsonProperty("damage") int damage
    ) {
        this.name = name;
        this.damage = damage;
    }

    public Attack() {
    }

    private void validateAttack(String name, int damage) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttackException(name);
        }
        if (damage < 0) {
            throw new InvalidAttackException(damage);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateAttack(name, this.damage);
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        validateAttack(this.name, damage);
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "name='" + name + '\'' +
                ", damage=" + damage +
                '}';
    }
}