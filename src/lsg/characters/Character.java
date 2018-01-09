package lsg.characters;
import java.lang.String;
import java.util.Locale;

import javafx.beans.property.SimpleDoubleProperty;
import lsg.bags.Bag;
import lsg.bags.Collectible;
import lsg.bags.SmallBag;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.exceptions.lsg.exceptions.BagFullException;
import lsg.helpers.*;
import lsg.weapons.*;

import static lsg.bags.Bag.transfer;


public abstract class Character {
    public static final String LIFE_STAT_STRING = "life";
    public static  final String STAM_STAT_STRING = "stamina";
    public static final String BUFF_STAT_STRING = "Buff";
    public static final String PROTECTION_STAT_STRING = "Armor";
    private Bag bag;
    private String name;
    private SimpleDoubleProperty lifeRate;

    public double getStaminaRate() {
        return staminaRate.get();
    }

    public SimpleDoubleProperty staminaRateProperty() {
        return staminaRate;
    }

    private SimpleDoubleProperty staminaRate;
    private int life;
    private int maxLife;
    private int stamina;
    private int maxStamina;
    private Dice dice;
    private Weapon weapon;
    private Consumable consumable;

    public Character (String name) {
        this.name = name;
        this.dice = new Dice(101);
        this.bag = new SmallBag();
        this.lifeRate = new SimpleDoubleProperty();
        this.staminaRate = new SimpleDoubleProperty();
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    protected void setLife(int life) {
        this.life = life;
        this.lifeRate.set((double)life/maxLife);
    }

    public int getMaxLife() {
        return maxLife;
    }

    protected void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
        this.lifeRate.set((double)life/maxLife);
    }

    public int getStamina() {
        return stamina;
    }

    protected void setStamina(int stamina) {
        this.stamina = stamina;
        this.staminaRate.set((double)stamina/maxStamina);
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    protected void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
        this.staminaRate.set((double)stamina/maxStamina);
    }

    public void printStats () {
        System.out.println(this.toString());
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public Weapon getWeapon() { return weapon; }

    private void repairWeaponWith(RepairKit kit) throws WeaponNullException, ConsumeNullException {
        if (this.weapon == null) {
            throw new WeaponNullException();
        }
        System.out.println(this.name + " repairs " + this.weapon.toString() + " with " + kit);
        this.weapon.repairWith(kit);
    }

    /**   Les methodes de BAG   **/

    public boolean bagIsNull (){
        return this.bag == null;
    }

    public void pickUp(Collectible item) throws NoBagException, BagFullException {
        if (this.bag == null ) throw new NoBagException();
        if(this.bag.getCapacity() != this.bag.getWeight() && (this.bag.getWeight() + item.getWeight()) <= this.bag.getCapacity() ) {
            this.bag.push(item);
            System.out.print(this.name+" picks up "+ item);
        }
    }

    public Collectible pullOut(Collectible item) throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        Collectible it = null;
        if (this.bag.contains(item)){
            it = this.bag.pop(item);
            System.out.print(this.name+" pulls out "+ it);
        }
        return it;
    }

    public void printBag(){
        System.out.println("BAG : "+this.bag);
    }


    public int getBagCapacity() throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        return this.bag.getCapacity();
    }

    public  int getBagWeight() throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        return this.bag.getWeight();
    }

    public Collectible[] getBagItems() throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        return this.bag.getItems();
    }

    public Bag setBag(Bag bag) throws BagFullException {
        transfer(this.bag,bag);
        Bag ancien = this.bag;
        if (bag != null) {
            System.out.println(this.name + " changes " + this.bag.getClass().getSimpleName() + " for " + bag.getClass().getSimpleName());
        }else if (this.bag == null){
            System.out.println(this.name + " changes " + null + " for " + bag.getClass().getSimpleName());
        }else {
            System.out.println(this.name + " changes " + this.bag.getClass().getSimpleName() + " for " + null);
        }
        this.bag = bag;
        return ancien;
    }

    public void equip(Weapon weapon) throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        if (this.bag.contains(weapon)){
            this.setWeapon(weapon);
            this.pullOut(weapon);
            System.out.println(" and equips it !");
        }
    }

    public void equip(Consumable consumable) throws NoBagException {
        if (this.bag == null ) throw new NoBagException();
        if (this.bag.contains(consumable)){
            this.setConsumable(consumable);
            this.pullOut(consumable);
            System.out.println(" and equips it !");
        }
    }

    /***********************/

    /**  Les methodes de Consumable  **/

    public Consumable getConsumable() {
        return this.consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    private void drink(Drink drink) throws ConsumeNullException {
        if (drink == null) {
            throw new ConsumeNullException();
        } else {
            String d = drink.toString();
            int cap = drink.use();
            this.stamina = (this.stamina + cap) > this.maxStamina ? this.maxStamina : this.stamina + cap;
            System.out.println(this.name + " drinks " + d);
        }
    }

    private void eat(Food food) throws ConsumeNullException {
        if (food == null) {
            throw new ConsumeNullException();
        } else {
            String f = food.toString();
            int cap = food.use();
            this.life = (this.life + cap) > this.maxLife ? this.maxLife : this.life + cap;
            System.out.println(this.name + " eats " + f);
        }
    }

    public void use(Consumable consumable) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
        if(consumable == null) {
            throw new ConsumeNullException();
        }
        if (consumable.getCapacity() == 0){
            throw new ConsumeEmptyException();
        }
        if (consumable instanceof Drink) {
            drink((Drink) consumable);
        } else if (consumable instanceof Food) {
            eat((Food) consumable);
        } else if (consumable instanceof RepairKit) {
            try {
                repairWeaponWith((RepairKit) consumable);
            } catch (WeaponNullException e) {
                throw new ConsumeRepairNullWeaponException();
            }

        }
    }

    public boolean bagContains (Collectible item){
        return this.bag.contains(item);
    }

    public void consume() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException {
        this.use(this.consumable);
    }

    /***********************/

    private Consumable fastUseFirst(Class<? extends Consumable> type) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException {
        Consumable cons = null;
        for (Collectible item : this.bag.getItems()) {
            if (type.isInstance(item)){
                cons = (Consumable) item;
                this.use(cons);
                if (cons.getCapacity() == 0) {
                    this.pullOut(item);
                }
                return cons;
            }

        }
        return cons;
    }

    public Drink fastDrink() throws ConsumeNullException, ConsumeEmptyException, NoBagException {
        try {
            System.out.println(this.name + " drinks FAST :");
            return (Drink) fastUseFirst(Drink.class);
        }catch (ConsumeRepairNullWeaponException e){}
        return null;
    }

    public Food fastEat() throws ConsumeNullException, ConsumeEmptyException, NoBagException {
        try {
            System.out.println(this.name + " eats FAST :");
            return (Food) fastUseFirst(Food.class);
        }catch (ConsumeRepairNullWeaponException e){}
        return null;
    }
    public RepairKit fastRepair() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException {
        System.out.println(this.name + " repairs FAST :");
        return (RepairKit) fastUseFirst(RepairKit.class);

    }
    /***********************/

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void printWeapon() {
        if (this.weapon != null)
            System.out.println("WEAPON : "+this.weapon.toString()) ;
        else
            System.out.println("WEAPON : "+null) ;
    }



    private int attackWith(Weapon weapon) throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
        if (weapon == null) throw new WeaponNullException();
            int roll, degats;
            if (weapon.isBroken()) {
                //degats = 0;
                throw new WeaponBrokenException(weapon);
            } else {
                roll = dice.roll();
                if (roll == 0) {
                    degats = weapon.getMinDamage();
                } else if (roll == 100) {
                    degats = weapon.getMaxDamage();
                } else {
                    degats = weapon.getMinDamage() + (int) (((weapon.getMaxDamage() - weapon.getMinDamage()) * ((float) roll / 100)));
                }

            }

            if(this.stamina == 0){
                throw new StaminaEmptyException();
            }else {
            if (((float) this.stamina / weapon.getStamCost()) >= 1.0) {
                this.stamina -= weapon.getStamCost();
            } else {
                degats = Math.round(degats * ((float) this.stamina / weapon.getStamCost()));
                this.stamina = 0;
            }
            weapon.use();
            degats = degats + Math.round(degats * this.computeBuff() / 100);
            return degats;
            }
    }

    public int attack () throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
        return attackWith(this.weapon);
    }

    public int getHitWith(int value){
        int v;
        float protection = this.computeProtection();
        if ( protection > 100.0f) {
            v = 0;
        }else {
            v = value - Math.round(value * protection/100);
        }
        this.life -= v > this.life ? this.life:v;
        return v;
    }

    public void printConsumable() {
        if(this.consumable == null)
            System.out.println("CONSUMABLE : "+null);
        else {
            System.out.println("CONSUMABLE : " + this.consumable.toString());
        }
    }

    protected  abstract float computeProtection();
    protected  abstract float computeBuff();

    @Override
    public String toString() {
        if (isAlive()) {
            return String.format("%-20s %-20s "+LIFE_STAT_STRING.toUpperCase()+":%-10s "+STAM_STAT_STRING.toUpperCase()+":%-10s PROTECTION:%-10s BUFF:%-10s(ALIVE)","[ "+getClass().getSimpleName()+" ]",this.getName(),String.format("%5d",this.getLife()),String.format("%5d",this.getStamina()),String.format(Locale.US,"%6.2f",this.computeProtection()),String.format(Locale.US,"%6.2f",this.computeBuff()));
        }
        return String.format("%-20s %-20s "+LIFE_STAT_STRING.toUpperCase()+":%-10s "+STAM_STAT_STRING+":%-10s PROTECTION:%-10s BUFF:%-10s","[ "+getClass().getSimpleName()+" ]",this.getName(),String.format("%5d",this.getLife()),String.format("%5d",this.getStamina()),String.format(Locale.US,"%6.2f",this.computeProtection()),String.format(Locale.US,"%6.2f",this.computeBuff()));
    }

    public double getLifeRate() {
        return lifeRate.get();
    }

    public SimpleDoubleProperty lifeRateProperty() {
        return lifeRate;
    }
}
