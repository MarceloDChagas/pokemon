package com.ptcg.pokemon_api.model.valueObject;

import java.util.Arrays;
import java.util.List;

public class PokemonStatus {
    private int hp;
    private List<Attack> attack;

    public PokemonStatus(int hp, List<Attack> attack) {
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

    public boolean isValidAttack(List<Attack> attack) {
        if (attack == null || attack.size() == 0) {
            return false; 
        }
        for (Attack atk : attack) {
            if (atk == null || atk.getName() == null || atk.getName().isEmpty() || atk.getDamage() < 0) { 
                return false; 
            }
        }
        return true;
    }

    public int getHp() {
        return hp;
    }

    public List<Attack> getAttack() {
        return attack;
    }
}
