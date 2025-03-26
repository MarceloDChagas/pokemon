package com.ptcg.pokemon_api.model.valueObject;

public class Attack {
    private String name;
    private int damage;

    public Attack(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
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
