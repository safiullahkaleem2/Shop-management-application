package model;

import java.util.ArrayList;
//The CreditSales class represents a system for managing sales and returns of items on credit. It contains two methods
// for recording sales and credit returns. The sales method takes in an item name, creditor name, quantity, and price,
// and if the item is found in the inventory with enough quantity, reduces the quantity of the item, adds the owed price
// to the creditor, and prints a success message. The recordCreditReturn method takes in an item name, creditor name,
// quantity, and price, and if the creditor is found in the creditors list, increases the quantity of the item,
// subtracts the owed price from the creditor, and prints a success message. The class relies on the Creditors and
// Inventory classes for managing creditors and inventory, respectively.


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
        Item item = Inventory.getInventory().giveItem(itemName);
        if (null == Creditors.getCreditor(creditorName)) {
            creditor = new Creditor(creditorName);
            Creditors.addCreditors(creditor);

        } else {
            creditor = Creditors.getCreditor(creditorName);
        }
        item.reduceQuantity(quantity);
        creditor.addOwed(price);

    }



    //
    // Requires: itemName, creditorName, quantity, and price are not null and not negative
    //Modifies: Inventory, creditors.
    //Effects: If the creditor is found in the creditors list, increases the quantity of the item,
    //subtracts the owed price from the creditor, and prints "Credit return recorded".
    //Otherwise, prints appropriate error message.

    public void recordCreditReturn(String itemName, String creditorName, int quantity, double price) {
        Creditor creditor = Creditors.getCreditor(creditorName);
        Item item = Inventory.getInventory().giveItem(itemName);
        increaseInventory(item, creditor, quantity, price);


    }

    // Requires: item, creditor, quantity, and price are not null and non-negative
    //Modifies: Inventory, creditors.
    // Effects: If the item is found in the inventory, increases the quantity of the item,
    //  subtracts the owed price from the creditor, and prints "Credit return recorded".
    //  Otherwise, prints appropriate error message.
    public void increaseInventory(Item item, Creditor creditor, int quantity, double price) {
        item.increaseQuantity(quantity);
        creditor.subtractOwed(price);
        System.out.println("Credit return recorded");


    }




}