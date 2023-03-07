package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String itemName, int quantity, String unit, int threshold, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(unit, item.getUnit());
        assertEquals(threshold, item.getThreshold());


    }
}
