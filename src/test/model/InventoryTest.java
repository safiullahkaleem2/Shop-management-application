package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;
    private Item item;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;

    private Bank bank;


    public InventoryTest() {
    }
    @BeforeEach
    void runBefore(){
        inventory = Inventory.getInventory();
        item = new Item("item1", 10, "unit1", 5);
        item2 = new Item("item2", 10, "unit1", 5);
        item3 = new Item("item3", 10, "unit1", 5);
        item4 = new Item("item4", 10, "unit1", 5);
        item5 = new Item("item5", 10, "unit1", 5);
        Bank.getBank(50000);
        bank = Bank.getBank();

    }

    @AfterEach
    void after(){
        inventory.getInventoryItems().clear();
        inventory = null;
        bank = null;
        item = null;
        item3 = null;
        item2 = null;
        item4 = null;
        item5 = null;

    }

    @Test
    void giveItemTest(){
        assertEquals(null,inventory.giveItem("lora"));
    }
    @Test
    void addItemTest() {
        assertTrue(inventory.addItem(item));
        assertFalse(inventory.addItem(item));
        assertTrue(inventory.addItem(item3));
        assertTrue(inventory.addItem(item4));
        assertTrue(inventory.addItem(item5));
        assertFalse(inventory.addItem(item2));
        assertFalse(inventory.addItem(item));
    }

    @Test
    void removeItemTest() {
        inventory.addItem(item);
        inventory.addItem(item3);
        assertTrue(inventory.removeItem("item1"));
        assertFalse(inventory.removeItem("item2"));  //false case
        assertFalse(inventory.removeItem("F"));
        assertTrue(inventory.removeItem("item3"));
    }

    @Test
    void boughtItemTest() {
            assertTrue(inventory.boughtNewItem("item2", 5, "unit2", 10, 500));
            assertEquals(49500,bank.getBalance());
            assertEquals(item2.getItemName(),inventory.get(0).getItemName());



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
        assertEquals(2,inventory.length());
    }

    @Test
    void itemGet(){
        inventory.addItem(item);
        inventory.addItem(item2);
        assertEquals(item.getItemName(),inventory.get(0).getItemName());
    }

    @Test

    void inventoryItemsGet(){
        inventory.addItem(item);
        assertEquals(inventory.getInventoryItems(),inventory.getInventoryItems());
    }

}
