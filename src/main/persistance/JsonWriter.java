package persistance;

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
    private String destination;
    private String destinationC;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination,String destinationC) {
        this.destination = destination;
        this.destinationC = destinationC;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
        writer1 = new PrintWriter(new File(destinationC));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Inventory to file
    public void write(Inventory inventory,Creditors creditors) {
        JSONObject json = inventory.toJson();
        JSONObject json2 = creditors.toJson();
        saveToFile(json.toString(TAB));
        saveToFileC(json2.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
        writer1.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    private void saveToFileC(String json) {
        writer1.print(json);
    }
}
