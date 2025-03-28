package com.ptcg.pokemon_api.model.valueObject;

import java.util.List;
import com.ptcg.pokemon_api.exception.InvalidAttackException;
import com.ptcg.pokemon_api.exception.InvalidHpException;

public class PokemonStatus {
    private int hp;
    private List<Attack> attacks;

    public PokemonStatus(int hp, List<Attack> attacks) {
        validateHp(hp);
        validateAttackList(attacks);
        this.hp = hp;
        this.attacks = attacks;
    }

    private void validateHp(int hp) {
        if (hp < 0) {
            throw new InvalidHpException(hp);
        }
    }

    private void validateAttackList(List<Attack> attacks) {
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

    
}
