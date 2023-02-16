package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashSalesTest {
    private CashSales cashSales;
    private Item item;
    private Inventory inventory;
    private Bank bank;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        item = new Item("item1", 10, "piece", 5);
        inventory.addItem(item);
        bank = new Bank(30000);
        cashSales = new CashSales();
    }

    @Test
    public void testSalesSuccess() {
        cashSales.sales("item1", 5, 10);
        assertEquals(30010, Bank.getBalance());
        assertEquals(5, item.getQuantity());
    }

    @Test
    public void testSalesItemNotFound() {
        cashSales.sales("item2", 5, 10);
        assertEquals(30000, Bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testSalesInsufficientQuantity() {
        cashSales.sales("item1", 100, 10);
        assertEquals(30000, Bank.getBalance());
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testCashReturnSuccess() {
        cashSales.cashReturn("item1", 5, 10);
        assertEquals(29990, Bank.getBalance());
        assertEquals(15, item.getQuantity());
    }

    @Test
    public void testCashReturnItemNotFound() {
        cashSales.cashReturn("item2", 5, 10);
        assertEquals(30000, Bank.getBalance());
        assertEquals(10, item.getQuantity());
    }


}
