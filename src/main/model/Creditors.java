package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.List;
// The Creditors class represents a list of creditors. It contains methods for adding, removing, and retrieving
// creditors from the list, as well as methods for getting the total amount owed by all creditors and the number of
// creditors in the list. The class uses an ArrayList of Creditor objects to store the creditors. The addCreditors
// method adds a new creditor to the list, and the removeCreditor method removes a creditor with a specified name. The
// getCreditor method retrieves a creditor with a specified name, and returns null if the creditor is not found. The
// creditorsGet method returns the creditor at a specified index. The creditorsLength method returns the number of
// creditors in the list. The getTotalAmountOwed method returns the total amount owed by all creditors in the list.
// The size method returns the number of creditors in the list.

public class Creditors implements Writable {

    private static ArrayList<Creditor> creditors;

    public Creditors() {
        creditors =  new ArrayList<Creditor>();
    }

    // Requires: None
    // Modifies: creditors
    // Effects: Adds a new creditor to the list of creditors, prints a success message to the console
    public static void addCreditors(Creditor creditor) {
        creditors.add(creditor);

    }

    // Requires: An integer n within the range of the list of creditors
    // Modifies: None
    // Effects: Returns the creditor at the specified index
    public Creditor creditorsGet(int n) {
        return creditors.get(n);
    }

    // Requires: None
    // Modifies: None
    // Effects: Returns the number of creditors in the list
    public int creditorsLength() {
        return creditors.size();
    }

    // Requires: A string name representing the name of the creditor to be removed
    // Modifies: creditors
    // Effects: Removes the creditor with the specified name from the list, prints a
    // success message to the console if successful, or a failure message if the given name is not found

    public boolean removeCreditor(String name) {
        for (int n = 0;n < creditors.size(); n = n + 1) {
            Creditor creditor = creditors.get(n);
            if (name.equals(creditor.getName())) {
                creditors.remove(n);
                System.out.println("Creditor removed successfully");
                return true;
            }
        }
        System.out.println("Cant find the given Creditor please check the spelling");
        return false;
    }

    // Requires: A string name representing the name of the creditor to retrieve
    // Modifies: None
    // Effects: Returns the creditor with the specified name, or null if the creditor is not found
    public static Creditor getCreditor(String name) {
        for (Creditor creditor : creditors) {
            if (name.equals(creditor.getName())) {
                return creditor;
            }
        }

        return null;
    }

    // Requires: None
    // Modifies: None
    // Effects: Returns the total amount owed by all creditors in the list
    public double getTotalAmountOwed() {
        double total = 0.0;
        for (Creditor creditor : creditors) {
            total += creditor.getOwed();
        }
        return total;
    }

    // Requires: None
    // Modifies: None
    // Effects: Returns the number of creditors in the list
    public int size() {
        return creditors.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("creditors", creditorsToJson());
        return json;
    }

    // EFFECTS: returns items in Inventory as a JSON array
    private JSONArray creditorsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Creditor c : creditors) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public List<Creditor> getCreditors() {
        return creditors;
    }
}
