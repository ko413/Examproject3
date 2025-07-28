package src.creature.character;

import creature.Creature;
import weapon.Weapon;

public class SuperHero extends Hero {
    public SuperHero(String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
        this.setHp(this.getHp() - 30);
    }
    public void attack(Creature target) {
        int damage = (int)(this.getWeapon().getDamage() * 2.5);
        System.out.println(this.getName() + "は" + this.getWeapon().getName() + this.getWeapon().attackMessage());
        System.out.println(target.getName() + "に" + damage + "のダメージを与えた！");
        target.setHp(target.getHp() - damage);
        if (target.getHp() < 0) {
            target.setHp(0);
        }
    }
}
