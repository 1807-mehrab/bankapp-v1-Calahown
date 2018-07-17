package com.revature.bankingapp;

import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
	private int id;
    private String name;
    private String passcode = "password";
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }
    
    public String getPassCode() {
    	return passcode;
    }
    
    public int getCid() {
    	return id;
    }

    private void createAccount() {
        Account newAccount = new Account(this);
        accounts.add(newAccount); 
    }

    /* private void getAccounts() {
        for (Account account : accounts) {
            System.out.println(account);
        }
    } */

    public Customer (String name) {
        this.name = name;
        this.id = Main.badao.insertCustomer(this);
        createAccount();
    }
    
    public Customer (int id, String name, ArrayList<Account> accounts) {
    	this.id = id;
    	this.name = name;
    	this.accounts = accounts;
    }

    public Account displayAccountNumber(int index) {
        return accounts.get(index);
    }
}