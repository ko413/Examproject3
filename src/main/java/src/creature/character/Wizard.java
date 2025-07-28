package creature.character;

import creature.Character;
import creature.Creature;
import weapon.Wand;
import weapon.Weapon;

public class Wizard extends Character {
    private int mp;

    public Wizard(String name, int hp, int mp, Weapon weapon) {
        super(name, hp, weapon);
        this.mp = mp;
    }

    public Wizard(String name, int hp, int mp) {
        this(name, hp, mp, new Wand());
    }

    @Override
    public String attack(Creature target) {
        target.setHp(target.getHp() - 3);
        return this.getName() + "は石を投げた！<br>" + target.getName() + "に3のダメージを与えた！";
    }

    public String magic(Creature target) {
        int cost = this.getWeapon().getCost();
        if (this.mp < cost) {
            return "MPが足りない！";
        }
        this.mp -= cost;
        int damage = this.getWeapon().getDamage();
        target.setHp(target.getHp() - damage);
        return this.getName() + "は" + this.getWeapon().getName() + this.getWeapon().attackMessage()
                + "<br>" + target.getName() + "に" + damage + "のダメージを与えた！";
    }

    @Override
    public String showStatus() {
        return this.getName() + " (HP: " + this.getHp() + ", MP: " + this.mp + ")";
    }

    public int getMp() {
        return this.mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}