public class Items {

    /* Item List */
    protected final String[] itemList = {"Stick", "Long Sword", "Axe", "Energy Sword",
            "Health Potion", "Damage Boost Potion", "Poison Potion"};
    private final String[] effectList = {null, null, null, null,
            "+20H", "-20D", "-20H"};
    private final int[] itemDamageList = {10, 20, 25, 22,
            0, 0, 0};
    private String[] playerItems = new String[3];
    static int slot = 0;

    /* Get */

    public Items() {
        this.playerItems[0] = "Stick";
    }
    public Items(String[] playerItems) {
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

    public void setItem(String item) {
        if (playerItems[slot+1] == null) {
            this.playerItems[slot + 1] = item;
        } else {
            System.out.println("Inventory is full");
        }
    }

    public void removeItem(int itemSlot) {
        this.playerItems[itemSlot] = null;
    }

    public int getDamage(String value) {
        for (int i = 0; i <= itemDamageList.length - 1; i++) {
            if (value.equals(itemList[i])) {
                return itemDamageList[i];
            }
        }
        return -1;
    }

    public String getEffect(String value) {
        for (int i = 0; i <= effectList.length - 1; i++) {
            if (value.equals(itemList[i])) {
                return effectList[i];
            }
        }
        return "Error";
    }

    public int getItemIndex(String value) {
        for (int i = 0; i <= itemList.length - 1; i++) {
            if (value.equals(itemList[i])) {
                return i;
            }
        }
        return -1;
    }

    public String[] getItemList() {
        return itemList;
    }
    public String getItem(int i){
        return itemList[i];
    }

    public int useItem() {
        return getDamage(playerItems[slot]);
    }


    public String toString() {
        String s = "";
        for (int i = 0; i <= itemList.length - 1; i++) {
            if (i != itemList.length-1) {
                s += itemList[i] + ", ";
            } else {
                s += itemList[i] + " ";
            }
        }
        return s;
    }
}