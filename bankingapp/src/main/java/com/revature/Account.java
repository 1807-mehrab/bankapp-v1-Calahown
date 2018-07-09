package com.revature;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID accountnumber;
    private double balance;
    private Customer customer;

    public UUID getAccountNumber() {
        return accountnumber;
    }

    public double getBalance() {
        return balance;
    }

    public Account(Customer customer) {
        accountnumber = UUID.randomUUID();
        balance = 0.0;
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return accountnumber.toString();
    }

    public void deposit(double number) {
        balance += number;
    }

    public void withdrawal(Double number) {
        balance -= number;
    }

    public void checkBalance() {
        System.out.println(balance);
    }
}