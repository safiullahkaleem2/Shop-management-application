//package persistence;
//
//import model.Item;
//import model.Inventory;
//
//import org.junit.jupiter.api.Test;
//import persistance.JsonReader;
//import persistance.JsonWriter;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//class JsonWriterTest extends JsonTest {
//    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
//    //write data to a file and then use the reader to read it back in and check that we
//    //read in a copy of what was written out.
//
//    @Test
//    void testWriterInvalidFile() {
//        try {
//            Inventory inventory = Inventory.getInventory();
//            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
//            writer.open();
//            fail("IOException was expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testWriterEmptyWorkroom() {
//        try {
//            Inventory inventory = model.Inventory.getInventory();
//            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
//            writer.open();
//            writer.write(inventory);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
//            inventory = reader.read();
//
//            assertEquals(0, inventory.length());
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//
//    @Test
//    void testWriterGeneralWorkroom() {
//        try {
//            Inventory inventory = Inventory.getInventory();
//            inventory.addItem(new Item("item1", 4,"kgs",2));
//            inventory.addItem(new Item("item3", 50 ,"kgs",20));
//            JsonWriter writer = new JsonWriter("./data/inventorywriter.json");
//            writer.open();
//            writer.write(inventory);
//            writer.close();
//
//            JsonReader reader = new JsonReader("./data/inventorywriter.json");
//            inventory = reader.read();
//
//            List<Item> items = inventory.getItems();
//            assertEquals(2, items.size());
//            checkItem("item1",4, "kgs",2, items.get(0));
//            checkItem("item3", 50,"kgs",20, items.get(1));
//
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
//}