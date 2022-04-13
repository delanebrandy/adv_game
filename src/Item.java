import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private double defence;
    private int damage;

    //Final Item List -- with name, effects, and damage
    private final ArrayList<String> itemList = new ArrayList<>(Arrays.asList("Stick", "Long Sword", "Axe", "Energy Sword","Shield",
            "Great Shield"));

    private final ArrayList<Double> defenceList = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.25, 1.5));


    private final ArrayList<Integer> itemDamageList = new ArrayList<>(Arrays.asList(20, 25, 30, 35, 5, 15));

    //Constructors

    public Item(){
        this.name = "Stick";
        this.defence = 0;
        this.damage = 20;
    }

    public Item(String name, int damage) {
        this.name = name;
        this.defence = 0;
        this.damage = damage;
    }
    public Item(String name, String effect) {
        this.name = name;
        this.defence = 0;
        this.damage = 0;
    }

    public Item(String name){
        try {
            if (!name.equals("EMPTY")){
            int objPlace = itemList.indexOf(name); //Finds the index of the item in the list
            this.name = name;
            this.defence = defenceList.get(objPlace);
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
    public double getDefence(){
        return this.defence;
    }

    public int searchItemDamage(String name){
        return itemDamageList.get(itemList.indexOf(name));
    }

    public String getName(){
        return this.name;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getItemDamage(String name){
        return itemDamageList.get(itemList.indexOf(name));
    }


}