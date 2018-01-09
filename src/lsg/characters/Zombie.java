package lsg.characters;

import lsg.weapons.Weapon;

public class Zombie extends Monster{

    public Zombie() {
        super("Zombie");
        super.setLife(10);
        super.setStamina(10);
        this.setWeapon(new Weapon("Zombie's hands",1,20,1,1000));
    }

    @Override
    protected float computeProtection() {
        return 10.0f;
    }

    @Override
    protected float computeBuff() {
        return 0;
    }
}
