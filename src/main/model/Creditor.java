package model;

// The Creditor class represents a creditor entity in a financial system. It has a name and an amount of money owed to
// it. The class provides methods to add and subtract from the amount owed, record payment received, and get the name
// and amount owed.

import org.json.JSONObject;
import persistance.Writable;

public class Creditor implements Writable {
    private String name;
    private double owed;


    public Creditor(String name) {
        this.name = name;
        owed = 0;


    }


     // Requires: N/A
     // Modifies: owed, paymentDays
     // Effects: Increases the amount owed by the given amount.

    public void addOwed(double amount) {
        owed = owed + amount;
    }


     // Requires: N/A
     // Modifies: owed, paymentDays
     // Effects: Decreases the amount owed by the given amount.
     //
    public void subtractOwed(double amount) {
        owed -= amount;
    }

    // Requires: N/A
    //Modifies: N/A
    // Effects: Returns the amount owed of the creditor.
    public double getOwed() {
        return owed;
    }


     //Requires: N/A
     // Modifies: owed, Bank.balance, paymentDays
     // Effects: Decreases the amount owed by the given amount and increases the bank's balance by the same amount.
    // If the amount owed is now zero, resets the payment days to zero. Prints a payment received message to
    // the console.

    public void paymentReceived(double amount) {
        subtractOwed(amount);
        Bank.getBank().addBalance(amount);
        System.out.println("Payment received from" + this.getName());

    }


     // Requires: N/A
     //Modifies: N/A
     // Effects: Returns the name of the creditor.
    public String getName() {
        return name;
    }


     // Requires: N/A
     // Modifies: N/A
     // Effects: Returns the number of payment days.



     // Requires: N/A
     // Modifies: name
     // Effects: Sets the name of the creditor.

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("owed", Double.toString(owed));

        return json;
    }
}
