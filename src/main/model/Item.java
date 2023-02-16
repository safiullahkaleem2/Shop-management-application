package model;

public class Item {
    private final int itemNumber;
    private String itemName;
    private int quantity;
    private final String unit;

    private int threshold;
    int lastItemNo = 0;

    // Requires: Non-null String values for itemName and unit, and non-negative int value for threshold.
    // Effects: Creates a new Item object with the given values for itemName, quantity, unit, and threshold.
    //          Initializes itemNumber to a unique value greater than the last itemNumber.
    Item(String itemName, int quantity, String unit, int threshold) {


        this.itemNumber = lastItemNo + 1;
        lastItemNo =  itemNumber;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unit = unit;
        this.threshold = threshold;
    }

    // Effects: Returns the value of itemNumber.
    public int getItemNumber() {
        return itemNumber;
    }

    // Effects: Returns the value of itemName.
    public String getItemName() {
        return itemName;
    }

    // Effects: Returns the Quantity.
    public int getQuantity() {
        return quantity;
    }

    // Effects: Returns the units of item.
    public String getUnit() {
        return unit;
    }


    // Modifies: this
    // Effects: Sets the value of itemName to the given value.
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Modifies: this
    // Effects: Sets the value of quantity to the given value.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Modifies: this
    // Effects: Subtracts the given amount from the current value of quantity.
    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }


    // Effects: Returns the value of threshold.
    public int getThreshold() {
        return threshold;
    }

    // Modifies: this
    // Effects: Sets the value of threshold to the given value.
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    // Effects: Returns true if the current quantity is less than the threshold, false otherwise.
    public boolean belowThreshold() {
        return quantity < threshold;
    }

    // Modifies: this
    // Effects: Adds the given amount to the current quantity.
    public void increaseQuantity(int amount) {
        quantity = quantity + amount;
    }

    // Modifies: this
    // Effects: Subtracts the given amount from the current quantity.
    public void decreaseQuantity(int amount) {
        quantity = quantity - amount;
    }



}
