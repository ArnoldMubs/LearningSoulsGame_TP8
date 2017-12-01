package lsg;

import lsg.armor.ArmorItem;
import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.bags.Bag;
import lsg.bags.Collectible;
import lsg.bags.MediumBag;
import lsg.bags.SmallBag;
import lsg.buffs.BuffItem;
import lsg.buffs.rings.DragonSlayerRing;
import lsg.buffs.rings.Ring;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.rings.RingOfSwords;
import lsg.buffs.talismans.MoonStone;
import lsg.buffs.talismans.Talisman;
import lsg.characters.*;
import lsg.characters.Character;
import lsg.consumables.Consumable;
import lsg.consumables.MenuBestOfV4;
import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.exceptions.lsg.exceptions.BagFullException;
import lsg.weapons.Claw;
import lsg.weapons.ShotGun;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 */

public class LearningSoulsGame {
    public static final String BULLET_POINT = "\u2219";
    private Hero hero;
    private Monster monster;
    private Scanner scanner;

    private void refresh (){
        this.hero.printStats();
        System.out.println(this.hero.armorToString());
        this.hero.printRings();
        this.hero.printConsumable();
        this.hero.printWeapon();
        this.hero.printBag();
        System.out.println();
        this.monster.printStats();
        this.monster.printWeapon();
    }



    private void title() {
        String croisillons = "";
        String name = "#    The Learning Souls Game   #";
        for(int i = 0;i<32;i++){
            croisillons += "#";
        }
        System.out.println(croisillons);
        System.out.println(name);
        System.out.println(croisillons);

    }

    private void fight1v1 (){
        String message = "";
        this.scanner = new Scanner(System.in);
        int action;
        refresh();
        Character A = this.hero;
        Character B = this.monster;
        Character temp;
        int attack;
        while (A.isAlive() && B.isAlive()) {
            System.out.println();
            try {
                attack = A.attack();
            }catch(WeaponNullException e){
                attack  = 0;
                message = "WARNING : no weapoon has been equiped !!!";
            }catch(WeaponBrokenException e)
            {
                attack  = 0;
                message = "WARNING : pelle cassee is broken !";
            }catch(StaminaEmptyException e)
            {
                attack  = 0;
                message = "ACTION HAS NO EFFECT; no more stamina !!!";
            }

            if (A instanceof Hero){
                System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
                action = scanner.nextInt();
                while (action != 1 && action != 2){
                    System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
                    action = scanner.nextInt();
                }

                if (action == 1){
                    System.out.println(message);
                    System.out.println("!!!" + A.getName() +" attacks "+ B.getName() + " with "+A.getWeapon()+"(ATTACK:"+attack+" | DMG: "+B.getHitWith(attack)+")");
                }else if (action == 2) {
                    try {
                        A.consume();
                    }catch(ConsumeNullException e){
                    System.out.println("IMPOSSIBLE ACTION : no consumable has been equiped !");
                    }catch(ConsumeEmptyException e){
                        A.printConsumable();
                        System.out.println(A.getConsumable().getName() + " is empty !");
                    }catch(ConsumeRepairNullWeaponException e){
                    System.out.println("IMPOSSIBLE ACTION : no weapon has been equiped !");
                }
                }
            }else {
                System.out.println(message);
                System.out.println("!!!" + A.getName() +" attacks "+ B.getName() + " with "+A.getWeapon()+"(ATTACK:"+attack+" | DMG: "+B.getHitWith(attack)+")");
            }
            refresh();
            temp=A;
            A=B;
            B=temp;

        }
        if (this.hero.isAlive() ) {
            System.out.print(" --- "+this.hero.getName()+" WINS !!!---");
        }else {
            System.out.print(" --- "+this.monster.getName()+" WINS !!!---");
        }
    }

    private void init (){
        Weapon sword = new Sword();
        ArmorItem protect = new DragonSlayerLeggings();
        Ring death = new RingOfDeath();
        Ring dragon = new DragonSlayerRing();
        Talisman tali = new MoonStone();
        Consumable hamburger = new Hamburger();
        this.hero = new Hero();
        hero.setRing(death,1);
        hero.setRing(dragon,2);
        death.setHero(this.hero);
        dragon.setHero(this.hero);
        this.hero.setArmorItem(protect,1);
        this.hero.setConsumable(hamburger);
        this.monster = new Lycanthrope();
        monster.setTalisman(tali);
        this.hero.setWeapon(sword);
    }

    public void createExhaustedHero(){

        this.hero = new Hero();
        this.hero.getHitWith(99);
        Weapon arme = new Weapon("Grosse Arme", 0, 0, 1000, 100);
        this.hero.setWeapon(arme);
        try {
            this.hero.attack();
        }catch(WeaponNullException e){
            e.printStackTrace();
        }
        catch(WeaponBrokenException e){
            e.printStackTrace();
        }catch(StaminaEmptyException e){
            e.printStackTrace();
        }
        System.out.println(this.hero.toString());

    }


    private void play_v1 (){
        init();
        fight1v1();
    }

    private void testExceptions() throws BagFullException {
        this.hero.setConsumable(new RepairKit());
        this.hero.setBag(null);
        //fight1v1();
    }
    public static void main(String args[]){

        LearningSoulsGame game = new LearningSoulsGame() ;
        game.init();
        game.refresh();
        try {
            game.testExceptions();
        } catch (BagFullException e) {
            e.printStackTrace();
        }
        game.refresh();
        //game.refresh();

    }
}
