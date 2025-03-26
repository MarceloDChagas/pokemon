package com.ptcg.pokemon_api.model.valueObject;

import java.util.Arrays;
import java.util.List;

public class PokemonStatus {
    private int hp;
    private List<String> attack;

    public PokemonStatus(int hp, List<String> attack) {
        if (!isValidHp(hp)) {
            throw new IllegalArgumentException("Invalid HP value: " + hp);
        }
        if (!isValidAttack(attack)) {
            throw new IllegalArgumentException("Invalid attack array: " + Arrays.toString(attack.toArray()));
        }
        this.hp = hp;
        this.attack = attack;
    }

    public boolean isValidHp(int hp) {
        return hp >= 0;
    }

    public boolean isValidAttack(List<String> attack) {
        if (attack == null || attack.size() == 0) {
            return false; 
        }
        for (String atk : attack) {
            if (atk == null || atk.trim().isEmpty()) { 
                return false; 
            }
        }
        return true;
    }

    public int getHp() {
        return hp;
    }

    public List<String> getAttack() {
        return attack;
    }
}
