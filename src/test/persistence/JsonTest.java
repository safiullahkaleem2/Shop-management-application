package persistence;

import model.Bank;
import model.Creditor;
import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String itemName, int quantity, String unit, int threshold, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(unit, item.getUnit());
        assertEquals(threshold, item.getThreshold());


    }

    protected void checkCreditor(String name, Double owed, Creditor creditor) {
        assertEquals(name, creditor.getName());
        assertEquals(owed, creditor.getOwed());


    }

    protected void checkBank(Double balance,Double receipts, Double payments, Bank bank) {
        assertEquals(balance, bank.getBalance());
        assertEquals(receipts, bank.getReceipts());
        assertEquals(payments, bank.getPayments());


    }
}
