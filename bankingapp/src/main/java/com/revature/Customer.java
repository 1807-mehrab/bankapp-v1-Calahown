package com.revature;

import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
    String name;
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
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
        createAccount();
    }

    public Account displayAccountNumber(int index) {
        return accounts.get(index);
    }
}