package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json","./data/noSuchFile.json","./data/noSuchFile.json");
        try {
            Inventory inventory = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json","./data/testReaderEmptyCreditors.json","./data/testReaderEmptyBank.json");
        try {
            Inventory inventory = reader.read();
            Creditors creditors = reader.readC();
            Bank bank =  reader.readB();

            assertEquals(0, inventory.length());
            assertEquals(0, creditors.size());
            assertEquals(0, bank.getBalance());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/InventoryReader.json","./data/CreditorsReader.json","./data/BankReader.json");
        try {
            Inventory inventory = reader.read();

            List<Item> items = inventory.getItems();
            assertEquals(2, items.size());
            checkItem("item1",4,"kgs",2, items.get(0));
            checkItem("item2",50,"kgs",20,items.get(1));
            Creditors creditors = reader.readC();
            List<Creditor> creditorsTest = creditors.getCreditors();
            checkCreditor("safi", 3000.0, creditorsTest.get(0));
            Bank bank = reader.readB();
            checkBank(26000.0,30000.0,4000.0,bank);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}