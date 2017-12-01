package lsg.bags;

import lsg.armor.BlackWitchVeil;
import lsg.armor.DragonSlayerLeggings;
import lsg.armor.RingedKnightArmor;
import lsg.consumables.food.Hamburger;
import lsg.exceptions.lsg.exceptions.BagFullException;
import lsg.weapons.ShotGun;
import lsg.weapons.Sword;

import java.util.HashSet;

import static lsg.LearningSoulsGame.BULLET_POINT;

public class Bag {
    private int capacity;
    private int weight;
    private HashSet<Collectible> items ;

    public Bag(int capacity) {
        this.capacity = capacity;
        this.items = new HashSet<Collectible>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWeight() {
        return weight;
    }

    public void push(Collectible item) throws BagFullException {
        if (item.getWeight()+this.weight >= this.capacity) throw new BagFullException(this);
        if (item.getWeight() <= this.capacity && (item.getWeight()+this.weight <=this.capacity)){
            this.items.add(item);
            this.weight += item.getWeight();
        }
    }

    public Collectible pop(Collectible item){
        Collectible itemCopy = item;
        if (this.contains(item)) {
            this.items.remove(item);
            this.weight -= item.getWeight();
            return itemCopy;
        }
        return null;
    }

    public boolean contains(Collectible item){
        return this.items.contains(item);
    }

    public Collectible[] getItems(){
        Collectible[] tabItems = new Collectible[this.items.size()];
        int i = 0;
        for (Collectible it : items){
            tabItems[i] = it;
            i++;
        }
        return tabItems;
    }

    public String toString() {
        if (this == null) return null;
        String bag = this.getClass().getSimpleName()+" [ "+this.items.size()+" items | "+this.weight+"/"+this.capacity+" kg ]\n";
        if(this.items.size()==0){
            return bag+BULLET_POINT+" (empty)";
        }
        for (Collectible it : items){
            bag += BULLET_POINT+" "+it.toString() +"["+ it.getWeight()+" kg]\n";
        }
        return bag;
    }

    public static void transfer(Bag from, Bag into) throws BagFullException {
        if (from==null || into ==null){
            return;
        }
        Collectible[] tabItem = from.getItems();
        for( Collectible it : tabItem){
            into.push(it);
            if(into.contains(it)){
                from.pop(it);
            }
        }
    }

    public static void main(String args[]){
        ShotGun shot  = new ShotGun();
        DragonSlayerLeggings dragon = new DragonSlayerLeggings();
        RingedKnightArmor ring  = new RingedKnightArmor();
        Bag bag = new Bag(10);
        Bag bag2 = new Bag(5);
        try {
            bag.push(shot);
            bag.push(dragon);
            bag.push(ring);
        } catch (BagFullException e) {
            e.printStackTrace();
        }
        System.out.println("Sac 1 :");
        System.out.println(bag.toString());
        System.out.println("Sac 2 :");
        System.out.println(bag2.toString());
        try {
            transfer(bag,bag2);
        } catch (BagFullException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("Sac 2 après transfert :");
        System.out.println(bag2.toString());
        System.out.println();
        System.out.println("Sac 1 après transfert :");
        System.out.println(bag.toString());
    }

}
