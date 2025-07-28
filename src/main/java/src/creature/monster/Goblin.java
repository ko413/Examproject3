package src.creature.monster;

import creature.Monster;
import creature.Creature;

public class Goblin extends Monster {
    public Goblin(char suffix, int hp) {
        super("ゴブリン", suffix, hp);
    }
    public void attack(Creature target) {
        System.out.println("ゴブリン" + getSuffix() + "はナイフで切りつけた" + target.getName() + "に8のダメージを与えた！");
        target.setHp(target.getHp() - 8);
        if (target.getHp() < 0) {
            target.setHp(0);
        }
    }
}
