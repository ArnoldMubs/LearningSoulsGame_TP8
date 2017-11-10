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
        this.monster.printStats();
        System.out.println(BULLET_POINT+" "+this.hero.getWeapon().toString());
        System.out.println(BULLET_POINT+" "+this.hero.getConsumable().toString());
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
        this.scanner = new Scanner(System.in);
        int action;
        refresh();
        Character A = this.hero;
        Character B = this.monster;
        Character temp;
        int attack;
        while (A.isAlive() && B.isAlive()) {
            System.out.println();
            if (A instanceof Hero){
                System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
                action = scanner.nextInt();
                while (action != 1 && action != 2){
                    System.out.print("Hero's action for next move : (1) attack | (2) consume > ");
                    action = scanner.nextInt();
                }

                if (action == 1){
                    attack = A.attack();
                    System.out.println("!!!" + A.getName() +" attacks "+ B.getName() + " with "+A.getWeapon().getName() +"(ATTACK:"+attack+" | DMG: "+B.getHitWith(attack)+")");
                }else if (action == 2){
                    A.consume();
                }
            }else {
                attack = A.attack();
                System.out.println("!!!" + A.getName() +" attacks "+ B.getName() + " with "+A.getWeapon().getName() +"(ATTACK:"+attack+" | DMG: "+B.getHitWith(attack)+")");
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

    /*
    private void init (){
        Weapon sword = new Sword();
        Weapon claw = new Claw();
        Consumable hamburger = new Hamburger();
        this.hero = new Hero();
        this.monster = new Monster();
        this.hero.setWeapon(sword);
        this.hero.setConsumable(hamburger);
        this.monster.setWeapon(claw);
    }
    */

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

    public void testBag() {
        this.hero = new Hero();
        ArmorItem ringed = new RingedKnightArmor();
        RingedKnightArmor ring  = new RingedKnightArmor();
        this.hero.setArmorItem(ringed,1);
        Character grego = this.hero;
        ShotGun shot  = new ShotGun();
        Consumable hamburger = new Hamburger();
        DragonSlayerLeggings dragon = new DragonSlayerLeggings();
        grego.pickUp(dragon);
        grego.pickUp(ring);
        grego.pickUp(shot);
        grego.setConsumable(hamburger);
        grego.consume();
        grego.pickUp(hamburger);
        System.out.println();
        grego.pullOut(hamburger);
        System.out.println();
        System.out.println();
        grego.printBag();
        System.out.println("Capacité : "+grego.getBagCapacity());
        System.out.println("Capacité utilisé : "+grego.getBagWeight());
        System.out.println();
        System.out.println("Composition du sac du hero : ");
        for (Collectible it: grego.getBagItems()) {
            System.out.println(it);
        }
        grego.setBag(new MediumBag());
        grego.equip(shot);
        grego.equip(new Hamburger());
        //grego.equip(ringed,1);
    }

    public void testBag2() {
        this.hero = new Hero();
        ShotGun shot  = new ShotGun();
        Weapon arme =  new Weapon("Grosse Arme",0,0,1000,100);
        DragonSlayerLeggings dragon = new DragonSlayerLeggings();
        ArmorItem ringed = new RingedKnightArmor();
        RingedKnightArmor ring  = new RingedKnightArmor();
        Consumable cofee = new Coffee();
        Consumable hamburger = new Hamburger();
        Consumable whisky = new Whisky();
        Consumable repair1 = new RepairKit();
        Consumable repair2 = new RepairKit();
        this.hero.setWeapon(arme);
        this.title();
        System.out.println();
        System.out.println("Create exhausted hero");
        this.hero.printStats();
        this.hero.pickUp(dragon);
        this.hero.pickUp(ring);
        this.hero.pickUp(shot);
        this.hero.printBag();
        this.hero.setBag(new MediumBag());
        this.hero.printBag();
        this.hero.pickUp(cofee);
        this.hero.pickUp(hamburger);
        this.hero.pickUp(whisky);
        this.hero.pickUp(repair1);
        this.hero.pickUp(repair2);
        this.hero.printBag();
        System.out.println("--- AVANT ---");
        this.hero.printStats();
        this.hero.armorToString();
        this.hero.printWeapon();
        this.hero.printBag();
        System.out.println("--- ACTION ---");
        System.out.println(this.hero.fastDrink());
        this.hero.fastEat();
        System.out.println();
        this.hero.equip(shot);
        this.hero.equip(dragon,1);
        this.hero.fastRepair();
        System.out.println("--- APRES ---");
        this.hero.printStats();
        this.hero.armorToString();
        this.hero.printWeapon();
        this.hero.printBag();

    }

    private void play_v1 (){
        init();
        fight1v1();
    }

    private void play_v2 (){
        Weapon sword = new Sword();
        Weapon claw = new Claw();
        ArmorItem ringed = new RingedKnightArmor();
        this.hero = new Hero();
        this.monster = new Monster();
        this.hero.setWeapon(sword);
        this.monster.setWeapon(claw);
        this.hero.setArmorItem(ringed,1);
        fight1v1();
    }

    private void play_v3 (){
        init();
        fight1v1();
    }

    public void createExhaustedHero(){
        this.hero = new Hero();
        this.hero.getHitWith(99);
        Weapon arme =  new Weapon("Grosse Arme",0,0,1000,100);
        this.hero.setWeapon(arme);
        this.hero.attack();
        System.out.println(this.hero.toString());
    }

    public void aTable(){
        Consumable cons;
        Character myhero = this.hero;
        MenuBestOfV4 menu = new MenuBestOfV4();
        Iterator it = menu.iterator();
        while (it.hasNext()){
            cons = (Consumable) it.next();
            myhero.use(cons);
            System.out.println(myhero.toString());
            System.out.println("Apres utilisation : "+cons.toString());
        }

    }

    public static void main(String args[]){

        Weapon sword = new Sword();
        Weapon shotGun = new ShotGun();
        Character rick = new Hero("Rick");
        Character zombie = new Monster("Zombie");
        rick.setWeapon(shotGun);

        /*   Test   */

        Character gregooninator = new Hero();
        gregooninator.setWeapon(sword);
        gregooninator.printStats();
        System.out.println("attaque avec "+sword.toString()+" > "+gregooninator.attack());
        gregooninator.printStats();
        System.out.println("attaque avec "+sword.toString()+" > "+gregooninator.attack());
        gregooninator.printStats();
        System.out.println("attaque avec "+sword.toString()+" > "+gregooninator.attack());
        gregooninator.printStats();
        System.out.println("attaque avec "+sword.toString()+" > "+gregooninator.attack());

        while(zombie.isAlive() && rick.getStamina() > 0){
            rick.printStats();
            zombie.printStats();
            System.out.println("!!!" +rick.getName()+" attack "+zombie.getName()+" with "+rick.getWeapon().getName()+" ("+rick.attack()+") !!! -> Effective DMG:000"+zombie.getHitWith(rick.attack())+"PV");
        }

        //le friendly est le plus optimal lorsque les fichiers sont sue le meme packages.
        //sinon le public est plus optimal.
        /*
        LearningSoulsGame game2 = new LearningSoulsGame() ;
        game2.createExhaustedHero();
        game2.aTable();

        LearningSoulsGame game = new LearningSoulsGame() ;
        game.title();
        game.play_v3();
        */
        LearningSoulsGame game = new LearningSoulsGame() ;
        game.testBag2();





    }
}
