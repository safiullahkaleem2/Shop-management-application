package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Inventory inventory = Inventory.getInventory();
            Creditors creditors = new Creditors();
            Bank.getBank(0);
            Bank bank = Bank.getBank();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json",
                    "./data/my\0illllegal:fileName.json","./data/my\0illlegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Inventory inventory = Inventory.getInventory();
            Bank.getBank(0);
            Bank bank = Bank.getBank();
            Creditors creditors = new Creditors();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json",
                    "./data/testWriterEmptyCreditors.json","./data/testWriterEmptyBank.json");
            writer.open();
            writer.write(inventory,creditors,bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json",
                    "./data/testWriterEmptyCreditors.json","./data/testWriterEmptyBank.json");
            inventory = reader.read();
            creditors = reader.readC();
            bank = reader.readB();

            assertEquals(0, inventory.length());
            assertEquals(0, creditors.size());
            assertEquals(0, bank.getBalance());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Inventory inventory = Inventory.getInventory();
            Creditors creditors = new Creditors();
            Bank.getBank(0);
            Bank bank = Bank.getBank();
            bank.setBalance(2000);
            bank.setReceipts(3000);
            bank.setPayments(400);
            Creditor creditor = new Creditor("turnip");
            creditor.addOwed(10000);
            Creditors.addCreditors(creditor);
            inventory.addItem(new Item("item1", 4,"kgs",2));
            inventory.addItem(new Item("item3", 50 ,"kgs",20));
            JsonWriter writer = new JsonWriter("./data/InventoryWriter.json",
                    "./data/CreditorsWriter.json","./data/BankWriter.json");
            writer.open();
            writer.write(inventory,creditors,bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/InventoryWriter.json",
                    "./data/CreditorsWriter.json","./data/BankWriter.json");
            inventory = reader.read();
            creditors = reader.readC();
            bank = reader.readB();

            List<Item> items = inventory.getItems();
            assertEquals(2, items.size());
            checkItem("item1",4, "kgs",2, items.get(0));
            checkItem("item3", 50,"kgs",20, items.get(1));
            List<Creditor> creditorsTest = creditors.getCreditors();
            checkCreditor("turnip", 10000.0, creditorsTest.get(0));
            checkBank(2000.0,3000.0,400.0,bank);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}