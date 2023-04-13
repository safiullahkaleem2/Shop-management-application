package model;
//The CashSales class represents the sales and return transactions made using cash. It interacts with the Inventory and
// Bank classes to complete the transactions.

public class CashSales {


    // REQUIRES: itemName is not null
    // MODIFIES: Inventory, Bank
    // EFFECTS: If item with itemName exists in Inventory and quantity is less than or equal to the available quantity,
    // reduce the quantity of the item by the given quantity, add the price to the bank balance, and print "Transaction
    // successful and your bank balance is updated". Otherwise, print an appropriate error message.
    public void sales(String itemName, int quantity, double price) {
        Item item = Inventory.getInventory().giveItem(itemName);
        item.reduceQuantity(quantity);
        Bank.getBank().addBalance(price);
        EventLog.getInstance().logEvent(new Event("Performed Cash Sales"));

    }

    // REQUIRES: itemName is not null
    // MODIFIES: Inventory, Bank
    // EFFECTS: If item with itemName exists in Inventory, increase the quantity of the item by the given quantity,
    // subtract the price from the bank balance, and print "Return successfully recorded". Otherwise, print an
    // appropriate error message.
    public void cashReturn(String itemName, int quantity, double price) {
        Item item = Inventory.getInventory().giveItem(itemName);

        item.increaseQuantity(quantity);
        Bank.getBank().subtractBalance(price);
        EventLog.getInstance().logEvent(new Event("Performed a cash return"));
    }

}
