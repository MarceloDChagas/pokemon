package com.ptcg.pokemon_api.model.valueobject;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.exception.InvalidAttackException;
import com.ptcg.pokemon_api.exception.InvalidHpException;

public class PokemonStatus {

    private final int hp;

    @Field("attack")
    @JsonProperty("attack")
    private final List<Attack> attacks;

    @JsonCreator
    public PokemonStatus(
        @JsonProperty("hp") int hp,
        @JsonProperty("attack") List<Attack> attacks
    ) {
        validateHp(hp);
        validateAttacks(attacks);
        this.hp = hp;
        this.attacks = Collections.unmodifiableList(attacks);
    }

    private void validateHp(int hp) {
        if (hp < 0) {
            throw new InvalidHpException(hp);
        }
    }

    private void validateAttacks(List<Attack> attacks) {
        if (attacks == null || attacks.isEmpty()) {
            throw new InvalidAttackException(attacks);
        }
    }

    public int getHp() {
        return hp;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public int getAttackCount() {
        return attacks.size();
    }

    public int getAttackDamage(int index) {
        validateIndex(index);
        return attacks.get(index).getDamage();
    }

    public String getAttackName(int index) {
        validateIndex(index);
        return attacks.get(index).getName();
    }

    public boolean hasAttackNamed(String name) {
        return attacks.stream().anyMatch(a -> a.getName().equalsIgnoreCase(name));
    }

    public int getDamageByName(String name) {
        return attacks.stream()
                      .filter(a -> a.getName().equalsIgnoreCase(name))
                      .findFirst()
                      .orElseThrow(() -> new IllegalArgumentException("No attack found with name: " + name))
                      .getDamage();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= attacks.size()) {
            throw new IndexOutOfBoundsException("Invalid attack index: " + index);
        }
    }
} 
