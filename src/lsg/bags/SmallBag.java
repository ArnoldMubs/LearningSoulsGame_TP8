package lsg.bags;

import lsg.armor.BlackWitchVeil;
import lsg.armor.DragonSlayerLeggings;
import lsg.consumables.MenuBestOfV1;
import lsg.consumables.food.Hamburger;
import lsg.weapons.Sword;

public class SmallBag extends Bag {
    public SmallBag() {
        super(10);
    }

    public static void main(String args[]){
        BlackWitchVeil black  = new BlackWitchVeil();
        Hamburger hamb = new Hamburger();
        Sword sword = new Sword();
        DragonSlayerLeggings dragon = new DragonSlayerLeggings();
        SmallBag smalBag = new SmallBag();
        smalBag.push(black);
        smalBag.push(hamb);
        smalBag.push(sword);
        smalBag.push(dragon);
        System.out.println(smalBag.toString());
        Collectible sup = smalBag.pop(dragon);
        System.out.println("Pop sur "+sup);
        System.out.println();
        System.out.println(smalBag.toString());
    }
}


