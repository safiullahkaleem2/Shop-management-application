package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {


    @Test
    void test(){
        Bank.getBank(100);
        Bank.getBank().addBalance(50);
        assertEquals(150, Bank.getBank().getBalance());
        assertEquals(150, Bank.getBank().getReceipts());

        assertTrue(Bank.getBank().subtractBalance(75));
        assertEquals(75, Bank.getBank().getBalance());
        assertEquals(75, Bank.getBank().getPayments());
        assertEquals(150, Bank.getBank().getReceipts());

        assertFalse(Bank.getBank().subtractBalance(100));
        assertEquals(75, Bank.getBank().getBalance());
    }
}

