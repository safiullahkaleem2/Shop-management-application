package model;

public class CashSales {

    public CashSales() {

    }

    // REQUIRES: itemName is not null
    // MODIFIES: Inventory, Bank
    // EFFECTS: If item with itemName exists in Inventory and quantity is less than or equal to the available quantity,
    // reduce the quantity of the item by the given quantity, add the price to the bank balance, and print "Transaction
    // successful and your bank balance is updated". Otherwise, print an appropriate error message.
    public void sales(String itemName, int quantity, double price) {
        Item item = Inventory.giveItem(itemName);
        if (item == null) {
            System.out.println("Item not found in inventory");
        } else if (item.getQuantity() < quantity) {
            System.out.println("Not enough quantity in inventory");
        } else {
            item.reduceQuantity(quantity);
            Bank.addBalance(price);
            System.out.println("Transaction successful and your bank balance is updated");
        }

    }

    // REQUIRES: itemName is not null
    // MODIFIES: Inventory, Bank
    // EFFECTS: If item with itemName exists in Inventory, increase the quantity of the item by the given quantity,
    // subtract the price from the bank balance, and print "Return successfully recorded". Otherwise, print an
    // appropriate error message.
    public void cashReturn(String itemName, int quantity, double price) {
        Item item = Inventory.giveItem(itemName);
        if (item == null) {
            System.out.println("Item not found in inventory");
        } else {
            item.increaseQuantity(quantity);
            Bank.subtractBalance(price);
            System.out.println("Return successfully recorded");
        }
    }
}
