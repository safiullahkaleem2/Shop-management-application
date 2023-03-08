package persistance;

import model.Bank;
import model.Creditors;
import model.Inventory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of Inventory to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private PrintWriter writer1;
    private PrintWriter writer2;
    private String destination;
    private String destinationC;
    private String destinationB;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination,String destinationC, String destinationB) {
        this.destination = destination;
        this.destinationC = destinationC;
        this.destinationB = destinationB;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
        writer1 = new PrintWriter(new File(destinationC));
        writer2 = new PrintWriter(new File(destinationB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Inventory to file
    public void write(Inventory inventory, Creditors creditors, Bank bank) {
        JSONObject json = inventory.toJson();
        JSONObject json2 = creditors.toJson();
        JSONObject json3 = bank.toJson();
        saveToFile(json.toString(TAB));
        saveToFileC(json2.toString(TAB));
        saveToFileB(json3.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
        writer1.close();
        writer2.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    private void saveToFileC(String json) {
        writer1.print(json);
    }

    private void saveToFileB(String json) {
        writer2.print(json);
    }
}
