package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashSalesTest {
    private CashSales cashSales;
    private Item item;
    private Bank bank;

    @BeforeEach
    public void setUp() {
        Inventory inventory = Inventory.getInventory();

        item = new Item("item1", 10, "piece", 5);
        inventory.addItem(item);
        Bank.getBank(30000);
        bank = Bank.getBank();
        cashSales = new CashSales();
    }

    @Test
    public void testSalesSuccess() {
        cashSales.sales("item1", 5, 10);
        assertEquals(30010, bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testSalesItemNotFound() {
        cashSales.sales("item2", 5, 10);
        assertEquals(30000, bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testSalesInsufficientQuantity() {
        cashSales.sales("item1", 100, 10);
        assertEquals(30000, bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testCashReturnSuccess() {
        cashSales.cashReturn("item1", 5, 10);
        assertEquals(30000, bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testCashReturnItemNotFound() {
        cashSales.cashReturn("item2", 5, 10);
        assertEquals(30000, bank.getBalance());
        assertEquals(10, item.getQuantity());
    }


}
