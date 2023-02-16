package model;


import model.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;
    private Item item;
    private Bank bank;


    public InventoryTest() {
        inventory = new Inventory();
        item = new Item("item1", 10, "unit1", 5);
        bank = new Bank(50000);

    }

    @Test
    void addItemTest() {
        assertTrue(inventory.addItem(item));
        assertFalse(inventory.addItem(item));
    }

    @Test
    void removeItemTest() {
        inventory.addItem(item);
        assertTrue(inventory.removeItem("item1"));
        assertFalse(inventory.removeItem("item2"));
    }

    @Test
    void boughtNewItemTest() throws InsufficientBalanceException {
        assertTrue(inventory.boughtNewItem("item2", 5, "unit2", 10, 500));
        assertEquals(49500,bank.getBalance());

    }

    @Test
    void isItemPresentTest() {
        inventory.addItem(item);
        assertTrue(inventory.isItemPresent("item1"));
        assertFalse(inventory.isItemPresent("item2"));
    }
}
