package model;


import model.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;
    private Item item;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;

    private Bank bank;


    public InventoryTest() {
        inventory = Inventory.getInventory();
        item = new Item("item1", 10, "unit1", 5);
        item2 = new Item("item2", 10, "unit1", 5);
        item3 = new Item("item3", 10, "unit1", 5);
        item4 = new Item("item4", 10, "unit1", 5);
        item5 = new Item("item5", 10, "unit1", 5);

        Bank.getBank(50000);
        bank = Bank.getBank();

    }

    @Test
    void addItemTest() {
        assertTrue(inventory.addItem(item));
        assertFalse(inventory.addItem(item));

        assertTrue(inventory.addItem(item3));
        assertTrue(inventory.addItem(item4));
        assertFalse(inventory.addItem(item5));

    }

    @Test
    void removeItemTest() {
        inventory.addItem(item);
        inventory.addItem(item3);
        assertTrue(inventory.removeItem("item1"));
        assertFalse(inventory.removeItem("item2"));
        assertFalse(inventory.removeItem("F"));
        assertTrue(inventory.removeItem("item3"));
    }

    @Test
    void boughtNewItemTest() {
        try {
            assertTrue(inventory.boughtNewItem("item2", 5, "unit2", 10, 500));
        } catch (InsufficientBalanceException e) {
            fail("Unexpected insufficient balance Exception");
        }


        assertEquals(49500,bank.getBalance());
        try {
            assertTrue(inventory.boughtNewItem("item2", 5, "unit2", 10, 5000000));
            fail("Unexpected insufficient balance Exception");
        } catch (InsufficientBalanceException e) {
           // expected exception
        }

        assertEquals(49500,bank.getBalance());
    }

    @Test
    void isItemPresentTest() {
        inventory.addItem(item);
        assertTrue(inventory.isItemPresent("item1"));
        assertFalse(inventory.isItemPresent("item2"));
    }

    @Test
    void itemSize(){
        inventory.addItem(item);
        inventory.addItem(item2);
        assertEquals(4,inventory.length());
    }

    @Test
    void itemGet(){
        inventory.addItem(item);
        inventory.addItem(item2);
        assertEquals(item2.getItemName(),inventory.get(0).getItemName());
    }


}
