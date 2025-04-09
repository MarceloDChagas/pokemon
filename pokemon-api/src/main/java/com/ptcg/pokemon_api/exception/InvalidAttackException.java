
package com.ptcg.pokemon_api.exception;

import java.util.List;

import com.ptcg.pokemon_api.model.valueobject.Attack;

public class InvalidAttackException extends RuntimeException {
    public InvalidAttackException(String attackName) {
        super("Invalid attack name: " + attackName);
    }

    public InvalidAttackException(int attackDamage) {
        super("Invalid attack damage: " + attackDamage);
    }

    public InvalidAttackException(List<Attack> attacks) {
        super("Invalid attack list: " + attacks);
    }
}
