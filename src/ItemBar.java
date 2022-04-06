import java.util.ArrayList;
import java.util.Scanner;

public class ItemBar {
    protected ArrayList<Item> inventory = new ArrayList<>();
    static int slot = 0;

    public ItemBar(){
        this.inventory = new ArrayList<>();
        this.inventory.add(new Item());
        this.inventory.add(new Item("EMPTY"));
        this.inventory.add(new Item("EMPTY"));
    }

    public void addItem(String itemName){
        this.inventory.add(new Item(itemName));
    }

    public void incSlot() {
        if (slot == 3) {
            slot = 0;
        } else {
            slot++;
        }
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int inSlot) {
        if (inSlot >= 0 && inSlot <= 3) {
            slot = inSlot;
        } else {
            System.out.println("Slot is out of range - Slot will remain the same: " + slot);
            slot = 0;
        }
    }
    public String getCurrentItem() {
        return inventory.get(slot).getName();
    }

    public void setItem(String item) {
        if (this.inventory.contains("EMPTY")) {
            this.inventory.set(this.inventory.indexOf("EMPTY"), new Item(item));

        } else if (this.inventory.contains(item)) {
            System.out.println("Item already in inventory");
        } else {
            System.out.println("Inventory is full");


            Scanner sc = new Scanner("Drop an item to make room: yes or no\n");
            String input = sc.nextLine();
            if (input.equals("yes")) {
                inventory.remove(slot + 1);
                setItem(item);
            }
            else {
                System.out.println("Item not dropped");
            }

        }
    }

    public int getDamage() {
        return inventory.get(slot).getDamage();
    }
    public String getEffect() {
        return inventory.get(slot).getEffect();
    }

    public int attack() {
        return inventory.get(slot).getDamage();
    }
    public void use() {
        inventory.get(slot).useItem();
    }

}
