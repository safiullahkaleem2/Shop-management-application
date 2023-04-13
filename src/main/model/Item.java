package model;

import org.json.JSONObject;
import persistance.Writable;
//This is a class definition for an Item object, which has properties such as itemName, quantity, unit, and threshold.
// It provides methods to get and set these properties, as well as to modify the quantity of the item (increase or
// decrease) and check if the quantity is below the threshold.

public class Item implements Writable {

    private String itemName;
    private int quantity;
    private final String unit;

    private int threshold;


    // Requires: Non-null String values for itemName and unit, and non-negative int value for threshold.
    // Effects: Creates a new Item object with the given values for itemName, quantity, unit, and threshold.
    //          Initializes itemNumber to a unique value greater than the last itemNumber.
    public Item(String itemName, int quantity, String unit, int threshold) {



        this.itemName = itemName;
        this.quantity = quantity;
        this.unit = unit;
        this.threshold = threshold;
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

    //effect: convert the object to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("itemName", itemName);
        json.put("quantity", Integer.toString(quantity));
        json.put("unit", unit);
        json.put("threshold", Integer.toString(threshold));

        return json;
    }



}
