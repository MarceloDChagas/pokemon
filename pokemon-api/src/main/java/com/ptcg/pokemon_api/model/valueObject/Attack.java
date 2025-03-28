package com.ptcg.pokemon_api.model.valueObject;

import com.ptcg.pokemon_api.exception.InvalidAttackException;

public class Attack {
    private String name;
    private int damage;

    public Attack(String name, int damage) {
        validateAttack(name, damage);
        this.name = name;
        this.damage = damage;
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