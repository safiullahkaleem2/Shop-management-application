package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreditorsTest {
    private Creditors creditors;
    private Creditor creditor1;
    private Creditor creditor2;

    @BeforeEach
    public void setUp() {
        creditors = new Creditors();
        creditor1 = new Creditor("Safi");
        creditor2 = new Creditor("Karan");
    }

    @Test
    public void testAddCreditors() {
        Creditors.addCreditors(creditor1);
        assertEquals(1, creditors.size());
        assertEquals(creditor1, Creditors.getCreditor("Safi"));
    }

    @Test
    public void testRemoveCreditor() {
        Creditors.addCreditors(creditor1);
        Creditors.addCreditors(creditor2);

        assertTrue(creditors.removeCreditor("Safi"));
        assertEquals(1, creditors.size());
        assertEquals(creditor2, Creditors.getCreditor("Karan"));

        assertFalse(creditors.removeCreditor("Safi"));
        assertEquals(1, creditors.size());

        assertFalse(creditors.removeCreditor("Lassan"));
        assertEquals(1, creditors.size());
    }

    @Test
    public void testGetCreditor() {
        Creditors.addCreditors(creditor1);
        Creditors.addCreditors(creditor2);

        assertEquals(creditor1, Creditors.getCreditor("Safi"));
        assertEquals(creditor2, Creditors.getCreditor("Karan"));
        assertNull(Creditors.getCreditor("Lassan"));
    }

    @Test
    public void testGetTotalAmountOwed() {
        Creditors.addCreditors(creditor1);
        Creditors.addCreditors(creditor2);
        creditor1.addOwed(300);
        creditor2.addOwed(600);

        assertEquals(900.0, creditors.getTotalAmountOwed());
    }

    @Test
    public void testSize() {
        assertEquals(0, creditors.size());

        Creditors.addCreditors(creditor1);
        assertEquals(1, creditors.size());

        Creditors.addCreditors(creditor2);
        assertEquals(2, creditors.size());

        creditors.removeCreditor("Safi");
        assertEquals(1, creditors.size());
    }

    @Test
    public void testLength() {
        assertEquals(0, creditors.creditorsLength());

        Creditors.addCreditors(creditor1);
        assertEquals(1, creditors.creditorsLength());

        Creditors.addCreditors(creditor2);
        assertEquals(2, creditors.creditorsLength());

        creditors.removeCreditor("Safi");
        assertEquals(1, creditors.creditorsLength());
    }

    @Test
    public void testGet() {
        Creditors.addCreditors(creditor1);
        Creditors.addCreditors(creditor2);

        assertEquals(creditor1, creditors.creditorsGet(0));
        assertEquals(creditor2, creditors.creditorsGet(1));
        List<Creditor> testCreditors = new ArrayList<>();
        testCreditors.add(creditor1);
        testCreditors.add(creditor2);
        assertEquals(testCreditors,creditors.getCreditors());
    }
}
