package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {


    @Test
    void test(){
        Bank bank = new Bank(100);
        Bank.addBalance(50);
        assertEquals(150, Bank.getBalance());
        assertEquals(150, Bank.getReceipts());

        assertTrue(Bank.subtractBalance(75));
        assertEquals(75, Bank.getBalance());
        assertEquals(75, Bank.getPayments());
        assertEquals(150, Bank.getReceipts());

        assertFalse(Bank.subtractBalance(100));
        assertEquals(75, Bank.getBalance());
    }
}

