package src.creature.character;

import creature.Character;
import creature.Creature;
import weapon.Weapon;
public class Hero extends Character {

    public Hero(String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
    }

    public String attack(Creature target) {
        int damage = this.getWeapon().getDamage();
        target.setHp(target.getHp() - damage);
        // 表示したいメッセージを一つのStringとして組み立てます
        String message = this.getName() + "は" + this.getWeapon().getName() + this.getWeapon().attackMessage()
                + "<br>" + target.getName() + "に" + damage + "のダメージを与えた！";
        // 組み立てたメッセージを返します
        return message;
    }
}
