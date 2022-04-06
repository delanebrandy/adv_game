import java.util.ArrayList;

public class ItemBar {
    protected ArrayList<Item> itemList = new ArrayList<>();
    static int slot = 0;

    public ItemBar(){
        this.itemList = new ArrayList<>();
        this.itemList.add(new Item());
    }

    public void addItem(String itemName){
        this.itemList.add(new Item(itemName));
    }
}
