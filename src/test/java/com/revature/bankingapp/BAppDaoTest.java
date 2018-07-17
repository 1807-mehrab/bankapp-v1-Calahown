package com.revature.bankingapp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

public class BAppDaoTest {
	
	@Test
    public void BadaoGetAccount() {
    	Account a = null;
    	BAppDao badao = new BAppDao();
    	String string = "3f034719-bd88-434f-805e-30898ee3162c";
    	a = badao.getAccount(string);
    	
        assertEquals("3f034719-bd88-434f-805e-30898ee3162c", a.getAccountNumber().toString());
    }

    @Test
    public void BadaoGetCustomer() {
        Customer c = null;
        BAppDao badao = new BAppDao();
        ArrayList<Account> a = new ArrayList<>();
        c = badao.getCustomer(1, a);
        
        assertEquals(1, c.getCid());
    }
    
    @Test
    public void WithdrawTest() {
    	Account a = new Account(UUID.fromString("00000000-0000-0000-0000-000000000000"), 500, 1);
    	a.withdrawal(300.0);
    	assertEquals(200, (int)a.getBalance());
    }
    
    @Test
    public void DepositTest() {
    	Account a = new Account(UUID.fromString("00000000-0000-0000-0000-000000000000"), 0, 1);
    	a.deposit(300.0);
    	assertEquals(300, (int)a.getBalance());
    }
}
