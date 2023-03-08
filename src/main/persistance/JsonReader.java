package persistance;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Shop Status from JSON data stored in file
public class JsonReader {
    private String source;
    private String sourceC;
    private String sourceB;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source,String sourceC,String sourceB) {
        this.source = source;
        this.sourceC = sourceC;
        this.sourceB = sourceB;

    }

    // EFFECTS: reads Inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads Creditors from file and returns it;
    // throws IOException if an error occurs reading data from file

    public Creditors readC() throws IOException {
        String jsonData = readFile(sourceC);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCreditors(jsonObject);
    }
    // EFFECTS: reads Bank from file and returns it;
    // throws IOException if an error occurs reading data from file

    public Bank readB() throws IOException {
        String jsonData = readFile(sourceB);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBank(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inventory = Inventory.getInventory();
        addItems(inventory, jsonObject);
        return inventory;
    }

    // EFFECTS: parses Bank from JSON object and returns it
    private Bank parseBank(JSONObject jsonObject) {
        Bank.getBank(0);
        Bank bank = Bank.getBank();
        Double balance = Double.parseDouble(jsonObject.getString("balance"));
        Double payments = Double.parseDouble(jsonObject.getString("payments"));
        Double receipts = Double.parseDouble(jsonObject.getString("receipts"));
        bank.setBalance(balance);
        bank.setPayments(payments);
        bank.setReceipts(receipts);
        return bank;
    }

    // EFFECTS: parses Creditors from JSON object and returns it
    private Creditors parseCreditors(JSONObject jsonObject) {
        Creditors creditors = new Creditors();
        addCreditors(creditors, jsonObject);
        return creditors;
    }

    // MODIFIES: inventory
    // EFFECTS: parses items from JSON object and adds them to inventory
    private void addItems(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inventory, nextItem);
        }
    }

    // MODIFIES: inventory
    // EFFECTS: parses item from JSON object and adds it to inventory
    private void addItem(Inventory inventory, JSONObject jsonObject) {
        String itemName = jsonObject.getString("itemName");
        int quantity = Integer.parseInt(jsonObject.getString("quantity"));
        String unit = jsonObject.getString("unit");
        int threshold = Integer.parseInt(jsonObject.getString("threshold"));

        Item item = new Item(itemName,quantity,unit, threshold);
        inventory.addItem(item);
    }


    // MODIFIES: creditors
    // EFFECTS: parses creditor from JSON object and adds them to creditors
    private void addCreditors(Creditors creditors, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("creditors");
        for (Object json : jsonArray) {
            JSONObject nextCreditor = (JSONObject) json;
            addCreditor(creditors, nextCreditor);
        }
    }

    // MODIFIES: creditors
    // EFFECTS: parses creditor from JSON object and adds it to creditors
    private void addCreditor(Creditors creditors, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double owed = Double.parseDouble(jsonObject.getString("owed"));
        Creditor creditor = new Creditor(name);
        creditor.addOwed(owed);
        creditors.addCreditors(creditor);
    }
}
