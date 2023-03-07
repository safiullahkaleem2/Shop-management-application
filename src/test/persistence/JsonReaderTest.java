package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Inventory inventory = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            Inventory inventory = reader.read();

            assertEquals(0, inventory.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/inventory.json");
        try {
            Inventory inventory = reader.read();

            List<Item> items = inventory.getItems();
            assertEquals(2, items.size());
            checkItem("item1",4,"kgs",2, items.get(0));
            checkItem("item2",50,"kgs",20,items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}