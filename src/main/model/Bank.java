package model;

//The Bank class represents a bank that holds a balance and keeps track of receipts and payments.
//It is a singleton class, meaning there can only be one instance of it.
//The balance field holds the amount of money currently in the bank.
//The receipts and payments fields hold the total amount of money received and paid out by the bank.


import org.json.JSONObject;
import persistance.Writable;

public class Bank implements Writable {
    private double receipts;
    private double payments;
    private double balance;
    private static Bank bank;

    private Bank(double balance) {
        receipts = balance;
        payments = 0;
        this.balance = balance;
    }


    // effects: returns bank
    public static Bank getBank() {
        return bank;
    }


    //effects:initializes a new bank
    public static void getBank(double balance) {
        if (bank == null) {
            bank = new Bank(balance);
        }
    }

    // Requires: amount to be a valid double value
    // Modifies: balance and receipts fields of Bank class
    // Effects: adds the amount to the balance and receipts fields
    public void addBalance(double amount) {
        balance += amount;
        receipts += amount;
    }

    // Requires: amount to be a valid double value
    // Modifies: balance and payments fields of Bank class
    // Effects: subtracts the amount from the balance field, adds the amount to the payments field, and returns true.
    // If the balance is less than the amount, returns false without modifying the fields.
    public boolean subtractBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
            payments += amount;
            return true;
        } else {
            return false;
        }
    }

    // Effects: Returns the value of Balance
    public  double getBalance() {
        return balance;
    }

    public  void setBalance(double amount) {
        this.balance = amount;
    }

    public  void setPayments(double amount) {
        this.payments = amount;
    }

    public  void setReceipts(double amount) {
        this.receipts = amount;
    }

    // Effects: Returns the value of payments
    public double getPayments() {
        return payments;
    }

    // Effects: Returns the value of receipts.
    public double getReceipts() {
        return receipts;
    }

    //effect: convert the object to JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("receipts", Double.toString(receipts));
        json.put("payments", Double.toString(payments));
        json.put("balance", Double.toString(balance));


        return json;
    }
}
