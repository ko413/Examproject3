package creature.character;

import creature.Character;
import creature.Creature;
import weapon.Dagger;
import weapon.Weapon;

public class Thief extends Character {
    private boolean guard = false;

    public Thief(String name, int hp, Weapon weapon) {
        super(name, hp, weapon);
    }

    public Thief(String name, int hp) {
        this(name, hp, new Dagger());
    }

    @Override
    public String attack(Creature target) {
        int damage = this.getWeapon().getDamage() * 2;
        target.setHp(target.getHp() - damage);
        return this.getName() + "は素早く2回攻撃した！" + this.getWeapon().getName() + this.getWeapon().attackMessage()
                + "<br>" + target.getName() + "に" + damage + "のダメージを与えた！";
    }

    public String guard() {
        this.guard = true;
        return this.getName() + "は守りの体制に入った！";
    }

    @Override
    public void setHp(int hp) {
        if (this.guard) {
            this.guard = false;
            // メッセージはサーブレット側で表示するため、ここでは何もしません
            return;
        }
        super.setHp(hp);
    }

    // ガード状態か確認するメソッドを追加します
    public boolean isGuarding() {
        return this.guard;
    }
}