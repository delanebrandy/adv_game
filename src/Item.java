import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private String effect;
    private int damage;

    //Final Item List -- with name, effects, and damage
    private final ArrayList<String> itemList = new ArrayList<>(Arrays.asList("Stick", "Long Sword", "Axe", "Energy Sword","Shield",
            "Great Shield"));

    private final ArrayList<String> effectList = new ArrayList<>(Arrays.asList(null, null, null, null,"Protection",
            "Better Protection"));

    private final ArrayList<Integer> itemDamageList = new ArrayList<>(Arrays.asList(20, 25, 30, 35, 10, 15));

    //Constructors

    public Item(){
        this.name = "Stick";
        this.effect = null;
        this.damage = 20;
    }

    public Item(String name, int damage) {
        this.name = name;
        this.effect = null;
        this.damage = damage;
    }
    public Item(String name, String effect) {
        this.name = name;
        this.effect = effect;
        this.damage = 0;
    }

    public Item(String name){
        try {
            if (!name.equals("EMPTY")){
            int objPlace = itemList.indexOf(name); //Finds the index of the item in the list
            this.name = name;
            this.effect = effectList.get(objPlace);
            this.damage = itemDamageList.get(objPlace);
            }
        } catch (Exception e) {
            System.out.println("Item not found");
        }
    }

    //Getters and Setters

    public int getNumberOfItems(){
        return itemList.size();
    }
    public int getDamage(){
        return this.damage;
    }

    public int searchItemDamage(String name){
        return itemDamageList.get(itemList.indexOf(name));
    }

    public String getName(){
        return this.name;
    }
    public String getEffect(){
        return this.effect;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }

    public int shieldAttack(){
        if (this.name.equals("Shield")){
            return this.damage;
        } else if (this.name.equals("Great Shield")){
            return this.damage * 2;
        }
        else {
            return 0;
        }
    }

    public int getItemDamage(String name){
        return itemDamageList.get(itemList.indexOf(name));
    }


}