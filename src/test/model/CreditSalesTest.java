package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditSalesTest {

    private CreditSales creditSales;
    private Creditors creditors;
    private Inventory inventory;

    @BeforeEach
    public void setup() {
        creditSales = new CreditSales();
        creditors = new Creditors();
        inventory = Inventory.getInventory();
        Item item1 = new Item("item1", 10, "piece", 5);
        Item item2 = new Item("item2", 5, "piece", 10);
        inventory.addItem(item1);
        inventory.addItem(item2);
        inventory.giveItem("item1").getQuantity();
    }

    @Test
    public void testSales() {
        creditSales.sales("item1", "creditor1", 5, 10);

        assertEquals(1, creditors.size());
        Creditor creditor = creditors.getCreditor("creditor1");
        assertEquals(10, creditor.getOwed());
        assertEquals(13, inventory.giveItem("item1").getQuantity());

        creditSales.sales("item2", "creditor1", 5, 10);
        assertEquals(20, creditor.getOwed());
        assertEquals(0, inventory.giveItem("item2").getQuantity());

        creditSales.sales("item1", "creditor1", 13, 10);
        assertEquals(30, creditor.getOwed());
        assertEquals(0, inventory.giveItem("item1").getQuantity());
    }

    @Test
    public void testRecordCreditReturn() {
        creditSales.sales("item1", "creditor1", 5, 10);
        creditSales.recordCreditReturn("item1", "creditor1", 3, 6);
        Creditor creditor = creditors.getCreditor("creditor1");
        assertEquals(4, creditor.getOwed());
        assertEquals(8, inventory.giveItem("item1").getQuantity());

        creditSales.recordCreditReturn("item2", "creditor2", 2, 8);
        assertEquals(4, creditor.getOwed());
        assertEquals(8, inventory.giveItem("item1").getQuantity());

        creditSales.recordCreditReturn("item1", "creditor1", 10, 5);
        assertEquals(-1, creditor.getOwed());
        assertEquals(18, inventory.giveItem("item1").getQuantity());
    }
}
