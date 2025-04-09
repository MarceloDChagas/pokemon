package com.ptcg.pokemon_api.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.exception.InvalidAttackException;

import java.util.Objects;

public class Attack {

    private final String name;
    private final int damage;

    @JsonCreator
    public Attack(@JsonProperty("name") String name, @JsonProperty("damage") int damage) {
        validateAttack(name, damage);
        this.name = name;
        this.damage = damage;
    }

    private void validateAttack(String name, int damage) {
       validateName(name);
        validateDamage(damage);
    }

    private void validateDamage(int damage) {
        if (damage < 0) {
            throw new InvalidAttackException("Damage cannot be negative.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttackException("Name cannot be null or empty.");
        }
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Attack{" +
               "name='" + name + '\'' +
               ", damage=" + damage +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attack attack = (Attack) o;
        return damage == attack.damage && name.equalsIgnoreCase(attack.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), damage);
    }
} 
