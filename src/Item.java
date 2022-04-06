import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private String effect;
    private int damage;

    //Final Item List -- with name, effects, and damage
    protected final ArrayList<String> itemList = new ArrayList<>(Arrays.asList("Stick", "Long Sword", "Axe", "Energy Sword",
            "Health Potion", "Damage Boost Potion", "Poison Potion"));

    private final ArrayList<String> effectList = new ArrayList<>(Arrays.asList(null, null, null, null,
            "+20H", "2X", "-20H"));
    private final ArrayList<Integer> itemDamageList = new ArrayList<>(Arrays.asList(20, 25, 22,
            0, 0, 0));

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
            int objPlace = itemList.indexOf(name); //Finds the index of the item in the list
            this.name = name;
            this.effect = effectList.get(objPlace);
            this.damage = itemDamageList.get(objPlace);
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
    public String getName(){
        return this.name;
    }
    public String getEffect(){
        return this.effect;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }

    public int useItem(){
        if (this.effect == null){
            return this.damage;
        } else if (this.effect.equals("+20H")){
            return 20;
        }
        else if (this.effect.equals("2X")){
            return 2;
        }
        else if (this.effect.equals("-20H")){
            return -20;
        } else {
            return 0;
        }
    }


}
