package model;

import java.util.ArrayList;

public class CreditSales  {
    protected static ArrayList<Creditor> creditors;

    //
     // Requires: itemName, creditorName, quantity, and price are not null and non-negative
     // Modifies: Inventory, creditors.
     //Effects: If item is found in the inventory and there is enough quantity,
     // reduces the quantity of the item, adds the owed price to the creditor,
     //  and prints "Transaction successful". Otherwise, prints appropriate error message.
     //

    public void sales(String itemName, String creditorName, int quantity, double price) {
        Creditor creditor;
        Item item = Inventory.giveItem(itemName);
        if (item == null) {
            System.out.println("Item not found in inventory");
        } else if (item.getQuantity() < quantity) {
            System.out.println("Not enough quantity in inventory");
        } else {
            if (null == Creditors.getCreditor(creditorName)) {
                creditor = new Creditor(creditorName);
                Creditors.addCreditors(creditor);
            } else {
                creditor = Creditors.getCreditor(creditorName);
            }
            item.reduceQuantity(quantity);
            creditor.addOwed(price);
            System.out.println("Transaction successful");
        }
    }


    //
    // Requires: itemName, creditorName, quantity, and price are not null and not negative
    //Modifies: Inventory, creditors.
    //Effects: If the creditor is found in the creditors list, increases the quantity of the item,
    //subtracts the owed price from the creditor, and prints "Credit return recorded".
    //Otherwise, prints appropriate error message.

    public void recordCreditReturn(String itemName, String creditorName, int quantity, double price) {
        Creditor creditor = Creditors.getCreditor(creditorName);
        if (null == Creditors.getCreditor(creditorName)) {
            System.out.println("Can not find the given creditor");
        } else {
            Item item = Inventory.giveItem(itemName);
            increaseInventory(item, creditor, quantity, price);
        }

    }

    // Requires: item, creditor, quantity, and price are not null and non-negative
    //Modifies: Inventory, creditors.
    // Effects: If the item is found in the inventory, increases the quantity of the item,
    //  subtracts the owed price from the creditor, and prints "Credit return recorded".
    //  Otherwise, prints appropriate error message.
    public void increaseInventory(Item item, Creditor creditor, int quantity, double price) {
        if (item == null) {
            System.out.println("Item not found in inventory");
        } else {
            item.increaseQuantity(quantity);
            creditor.subtractOwed(price);
            System.out.println("Credit return recorded");
        }

    }




}