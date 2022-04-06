import java.util.*;

public class Items {

    /* Item List */
    protected final ArrayList<String> itemList = new ArrayList<>(Arrays.asList("Stick", "Long Sword", "Axe", "Energy Sword",
            "Health Potion", "Damage Boost Potion", "Poison Potion"));

    private final ArrayList<String> effectList = new ArrayList<>(Arrays.asList(null, null, null, null,
            "+20H", "-20D", "-20H"));
    private final ArrayList<Integer> itemDamageList = new ArrayList<>(Arrays.asList(20, 25, 22,
            0, 0, 0));
    private ArrayList<String> playerItems = new ArrayList<>(3);
    static int slot = 0;

    /* Get */

    public Items() {
        this.playerItems.add("Stick");
        this.playerItems.add("EMPTY");
        this.playerItems.add("EMPTY");
    }
    public Items(ArrayList<String> playerItems) {
        this.playerItems = playerItems;
    }

    public void incSlot() {
        if (slot == 3) {
            slot = 0;
        } else {
            slot++;
        }
    }
    public void decSlot() {
        if (slot == 0) {
            slot = 3;
        } else {
            slot--;
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
    public boolean inSlot(String item) {
        for (int i = 0; i <= playerItems.size() - 1; i++) {
            if (item.equals(playerItems.get(i))) {
                return true;
            }
        }
        return false;
    }
    public String getCurrentItem() {
        return playerItems.get(slot);
    }

    public void setItem(String item) {
        if (this.playerItems.contains("EMPTY")) {
            this.playerItems.set(this.playerItems.indexOf("EMPTY"), item);

        } else if (this.playerItems.contains(item)) {
            System.out.println("Item already in inventory");
        } else {
            System.out.println("Inventory is full");


            Scanner sc = new Scanner("Drop an item to make room: yes or no\n");
            String input = sc.nextLine();
            if (input.equals("yes")) {
                removeItem(slot + 1);
                setItem(item);
            }
            else {
                System.out.println("Item not dropped");
            }

        }
    }

    public void removeItem(int itemSlot) {
        this.playerItems.remove(itemSlot);
    }

    public ArrayList<String> getPlayerItems() {
        return this.playerItems;
    }

    public int getDamage(String value) {
        for (int i = 0; i <= itemDamageList.size() - 1; i++) {
            if (value.equals(itemList.get(i))) {
                return itemDamageList.get(i);
            }
        }
        return -1;
    }

    public String getEffect(String value) {
        for (int i = 0; i <= effectList.size() - 1; i++) {
            if (value.equals(itemList.get(i))) {
                return effectList.get(i);
            }
        }
        return "Error";
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }
    public String getItem(int i){
        return itemList.get(i);
    }

    public int useItem() {
        return getDamage(playerItems.get(slot));
    }

}