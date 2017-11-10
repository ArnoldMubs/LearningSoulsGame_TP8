package lsg.characters;
import java.lang.String;
import java.util.Locale;

import lsg.bags.Bag;
import lsg.bags.Collectible;
import lsg.bags.SmallBag;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
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
    }

    public int getMaxLife() {
        return maxLife;
    }

    protected void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getStamina() {
        return stamina;
    }

    protected void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    protected void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    public void printStats () {
        System.out.println(this.toString());
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public Weapon getWeapon() { return weapon; }

    private void repairWeaponWith(RepairKit kit){
        System.out.println(this.name+" repairs "+this.weapon.toString()+" with "+kit);
        this.weapon.repairWith(kit);
    }

    /**   Les methodes de BAG   **/

    public void pickUp(Collectible item) {
        if(this.bag.getCapacity() != this.bag.getWeight() && (this.bag.getWeight() + item.getWeight()) <= this.bag.getCapacity() ) {
            this.bag.push(item);
            System.out.print(this.name+" picks up "+ item);
        }
    }

    public Collectible pullOut(Collectible item) {
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

    public int getBagCapacity(){
        return this.bag.getCapacity();
    }

    public  int getBagWeight(){
        return this.bag.getWeight();
    }

    public Collectible[] getBagItems(){
        return this.bag.getItems();
    }

    public Bag setBag(Bag bag){
        transfer(this.bag,bag);
        Bag ancien = this.bag;
        System.out.println(this.name + " changes " + this.bag.getClass().getSimpleName() + " for " + bag.getClass().getSimpleName());
        this.bag = bag;
        return ancien;
    }

    public void equip(Weapon weapon){
        if (this.bag.contains(weapon)){
            this.setWeapon(weapon);
            this.pullOut(weapon);
            System.out.println(" and equips it !");
        }
    }

    public void equip(Consumable consumable){
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

    private void drink(Drink drink){
        String d = drink.toString();
        int cap = drink.use();
        this.stamina= (this.stamina + cap) > this.maxStamina ? this.maxStamina : this.stamina + cap;
        System.out.println(this.name+" drinks "+ d);
    }

    private void eat(Food food){
        String f = food.toString();
        int cap = food.use();
        this.life= (this.life + cap) > this.maxLife ? this.maxLife : this.life + cap;
        System.out.println(this.name+" eats "+f);
    }

    public void use(Consumable consumable){
        if (consumable instanceof Drink){
            drink((Drink) consumable);
        }else if (consumable instanceof Food){
            eat((Food) consumable);
        }else if (consumable instanceof RepairKit){
            repairWeaponWith((RepairKit) consumable);
        }
    }

    public boolean bagContains (Collectible item){
        return this.bag.contains(item);
    }

    public void consume(){
        this.use(this.consumable);
    }

    /***********************/

    private Consumable fastUseFirst(Class<? extends Consumable> type) {
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

    public Drink fastDrink(){ System.out.println(this.name + " drinks FAST :"); return (Drink) fastUseFirst(Drink.class);}
    public Food fastEat(){System.out.println(this.name + " eats FAST :");return (Food) fastUseFirst(Food.class); }
    public RepairKit fastRepair(){System.out.println(this.name + " repairs FAST :");return (RepairKit) fastUseFirst(RepairKit.class); }
    /***********************/

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public String printWeapon() {
        return this.weapon.toString();
    }

    private int attackWith(Weapon weapon) {
        int roll,degats;
        if (weapon.isBroken()){
            degats = 0;
        }else {
            roll = dice.roll();
            if (roll == 0){
                degats = weapon.getMinDamage();
            }else if (roll == 100){
                degats = weapon.getMaxDamage();
            }else {
                degats = weapon.getMinDamage() + (int)(((weapon.getMaxDamage() - weapon.getMinDamage())*((float)roll/100)));
            }

        }
        if (((float)this.stamina/weapon.getStamCost()) >= 1.0){
            this.stamina -= weapon.getStamCost();
        }
        else{
            degats = Math.round(degats * ((float)this.stamina/weapon.getStamCost()));
            this.stamina = 0;
        }
        weapon.use();
        degats = degats + Math.round(degats*this.computeBuff()/100);
        return degats;
    }

    public int attack (){
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

    protected  abstract float computeProtection();
    protected  abstract float computeBuff();

    @Override
    public String toString() {
        if (isAlive()) {
            return String.format("%-20s %-20s "+LIFE_STAT_STRING.toUpperCase()+":%-10s "+STAM_STAT_STRING.toUpperCase()+":%-10s PROTECTION:%-10s BUFF:%-10s(ALIVE)","[ "+getClass().getSimpleName()+" ]",this.getName(),String.format("%5d",this.getLife()),String.format("%5d",this.getStamina()),String.format(Locale.US,"%6.2f",this.computeProtection()),String.format(Locale.US,"%6.2f",this.computeBuff()));
        }
        return String.format("%-20s %-20s "+LIFE_STAT_STRING.toUpperCase()+":%-10s "+STAM_STAT_STRING+":%-10s PROTECTION:%-10s BUFF:%-10s","[ "+getClass().getSimpleName()+" ]",this.getName(),String.format("%5d",this.getLife()),String.format("%5d",this.getStamina()),String.format(Locale.US,"%6.2f",this.computeProtection()),String.format(Locale.US,"%6.2f",this.computeBuff()));
    }
}
