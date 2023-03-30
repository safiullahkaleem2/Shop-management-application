package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashSalesTest {
    private CashSales cashSales;
    private Item item;
    private Bank bank;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = Inventory.getInventory();

        item = new Item("item1", 10, "piece", 5);
        inventory.addItem(item);
        Bank.getBank(30000);
        bank = Bank.getBank();
        cashSales = new CashSales();
    }

    @AfterEach
    void after(){
        inventory.getInventoryItems().clear();

        item = null;


    }


    @Test
    public void testSalesSuccess() {
        cashSales.sales("item1", 5, 10);
        assertEquals(30010, bank.getBalance());
        assertEquals(5, item.getQuantity());
    }



    @Test
    public void testCashReturnSuccess() {
        cashSales.cashReturn("item1", 5, 10);
        assertEquals(30000, bank.getBalance());
        assertEquals(15, item.getQuantity());
    }


}
