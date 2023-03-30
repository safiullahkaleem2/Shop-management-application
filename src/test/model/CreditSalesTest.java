package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditSalesTest {

    private CreditSales creditSales;
    private Creditors creditors;
    private Inventory inventory;
    private Creditor creditor3;
    private Item item1;
    private Item item2;
    private Item item3;


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
        creditor3 = new Creditor("safi");
        creditors.addCreditors(creditor3);
    }

    @AfterEach
    void after(){
        inventory = null;
        creditors = null;
        item1 = null;
        item3 = null;
        item2 = null;
        creditSales = null;


    }

    @Test
    public void testSaleWithExistingCreditor() {
        // Sell item2 to an existing creditor
        creditSales.sales("item2", "safi", 2, 5);

        // Check that creditor1 was not added again to the Creditors object
        assertEquals(1, creditors.size());

        // Check that the item's quantity was reduced and creditor1's owed amount was updated
        assertEquals(3, inventory.giveItem("item2").getQuantity());
        assertEquals(5, Creditors.getCreditor("safi").getOwed());
    }

    @Test
    public void testSales() {
      creditSales.sales("item1","safi",5,3);
    }

    @Test
    public void testRecordCreditReturn() {

        creditSales.sales("item1", "safi", 5, 10);

        Creditor creditor = creditors.getCreditor("safi");
        assertEquals(10, creditor.getOwed());
        assertEquals(5, inventory.giveItem("item1").getQuantity());


        creditSales.recordCreditReturn("item1", "safi", 10, 5);
        assertEquals(5, creditor.getOwed());
        assertEquals(15, inventory.giveItem("item1").getQuantity());

    }
}
