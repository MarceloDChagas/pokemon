package com.ptcg.pokemon_api.model.valueObject;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptcg.pokemon_api.exception.InvalidAttackException;
import com.ptcg.pokemon_api.exception.InvalidHpException;

public class PokemonStatus {
    private int hp;
    
    @Field("attack")    
    private List<Attack> attack;

    public PokemonStatus() {}
    
    @JsonCreator
    public PokemonStatus(
        @JsonProperty("hp") int hp, 
        @JsonProperty("attack") List<Attack> attack) {
        validateHp(hp);
        validateAttackList(attack);
        this.hp = hp;
        this.attack = attack;
    }

    private void validateHp(int hp) {
        if (hp < 0) {
            throw new InvalidHpException(hp);
        }
    }

    private void validateAttackList(List<Attack> attack) {
        if (attack == null || attack.isEmpty()) {
            throw new InvalidAttackException(attack);
        }
    }

    public int getHp() {
        return hp;
    }

    public List<Attack> getAttacks() {
        return attack;
    }
}
