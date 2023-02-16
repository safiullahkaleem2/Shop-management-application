package model;

import model.exceptions.InsufficientBalanceException;

import java.util.ArrayList;


public class Inventory {
    protected static ArrayList<Item> inventory;
    protected static final int CAPACITY = 50;

    public Inventory() {
        inventory = new ArrayList<Item>();
    }

    // requires: item is not null
    // modifies: this
    // effects: if inventory is not full and the item is not already present in inventory,
    // add the item to inventory and return true, otherwise return false
    public boolean addItem(Item item) {
        if (inventory.size() < CAPACITY && !isItemPresent(item.getItemName())) {
            inventory.add(item);
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
        for (int n = 0;n < inventory.size(); n = n + 1) {
            Item item = inventory.get(n);
            if (name.equals(item.getItemName())) {
                inventory.remove(n);
                System.out.println("Item removed successfully");
                return true;

            }

        }
        System.out.println("Cannot find the Item. Please check your spelling");
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
        if (Bank.getBalance() < price) {
            throw new InsufficientBalanceException();
        }
        Bank.subtractBalance(price);
        Item item = new Item(name, quantity, unit, threshold);
        return inventory.add(item);
    }

    // requires: name is not null
    // modifies: none
    // effects: return true if an item with the given name is present in inventory, otherwise return false
    public boolean isItemPresent(String name) {
        for (Item item : inventory) {
            if (item.getItemName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // requires: name is not null
    // modifies: none
    // effects: if an item with the given name is present in inventory, return it, otherwise return null
    public static Item giveItem(String name) {
        for (int n = 0; n < inventory.size(); n++) {
            Item item = inventory.get(n);
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
        return inventory.size();
    }

    // requires: 0 <= n < inventory.size()
    // modifies: none
    // effects: return the item at the given index in inventory
    public Item get(int n) {
        return inventory.get(n);
    }

}
