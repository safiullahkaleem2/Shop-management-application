package model;

import model.exceptions.InsufficientBalanceException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;


public class Inventory implements Writable {
    private static Inventory inventory;
    private ArrayList<Item> items;
    protected static final int CAPACITY = 4;

    private Inventory() {
        items = new ArrayList<Item>();
    }

    public static Inventory getInventory() {
        if (inventory == null) {
            inventory = new Inventory();
        }
        return inventory;
    }

    // requires: item is not null
    // modifies: this
    // effects: if inventory is not full and the item is not already present in inventory,
    // add the item to inventory and return true, otherwise return false
    public boolean addItem(Item item) {
        if (inventory.items.size() < CAPACITY && !isItemPresent(item.getItemName())) {
            inventory.items.add(item);
            return true;
        } else {
            return false;
        }
    }

    // requires: name is not null
    // modifies: this.inventory
    // effects: if an item with the given name is present in inventory, remove it from inventory and return true,
    // otherwise print an error message and return false
    public boolean removeItem(String name) {
        for (int n = 0;n < inventory.items.size(); n = n + 1) {
            Item item = inventory.items.get(n);
            if (name.equals(item.getItemName())) {
                inventory.items.remove(n);
                System.out.println("Item removed successfully");
                return true;

            }

        }

        return false;
    }


    // requires: name and unit are not null, quantity, threshold and price are positive,
    // price is less than or equal to Bank.getBalance()
    // modifies: this.inventory, Bank
    // effects: create a new item with the given name, quantity, unit and threshold;
    // if the price is less than or equal to the balance in Bank, subtract the price from Bank balance,
    // add the item to inventory and return true, otherwise throw an InsufficientBalanceException and return false
    public boolean boughtNewItem(String name, int quantity, String unit, int threshold, double price)
            throws InsufficientBalanceException {
        if (Bank.getBank().getBalance() < price) {
            throw new InsufficientBalanceException();
        }
        Bank.getBank().subtractBalance(price);
        Item item = new Item(name, quantity, unit, threshold);
        return inventory.items.add(item);
    }

    // requires: name is not null
    // modifies: none
    // effects: return true if an item with the given name is present in inventory, otherwise return false
    public boolean isItemPresent(String name) {
        for (Item item : items) {
            if (item.getItemName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // requires: name is not null
    // modifies: none
    // effects: if an item with the given name is present in inventory, return it, otherwise return null
    public  Item giveItem(String name) {
        for (int n = 0; n < inventory.items.size(); n++) {
            Item item = inventory.items.get(n);
            if (item.getItemName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    // requires: none
    // modifies: none
    // effects: return the number of items in inventory
    public int length() {
        return inventory.items.size();
    }

    public Item get(int n) {
        return inventory.items.get(n);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns items in Inventory as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : inventory.items) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
