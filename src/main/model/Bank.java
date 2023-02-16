package model;

public class Bank {
    private static double receipts;
    private static double payments;
    private static double balance;

    public Bank(double balance) {
        receipts = balance;
        payments = 0;
        Bank.balance = balance;
    }

    // Requires: amount to be a valid double value
    // Modifies: balance and receipts fields of Bank class
    // Effects: adds the amount to the balance and receipts fields
    public static void addBalance(double amount) {
        balance += amount;
        receipts += amount;
    }

    // Requires: amount to be a valid double value
    // Modifies: balance and payments fields of Bank class
    // Effects: subtracts the amount from the balance field, adds the amount to the payments field, and returns true.
    // If the balance is less than the amount, returns false without modifying the fields.
    public static boolean subtractBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
            payments += amount;
            return true;
        } else {
            return false;
        }
    }

    // Effects: Returns the value of Balance
    public static double getBalance() {
        return balance;
    }

    // Effects: Returns the value of payments
    public static double getPayments() {
        return payments;
    }

    // Effects: Returns the value of receipts.
    public static double getReceipts() {
        return receipts;
    }
}
