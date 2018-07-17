package com.revature.bankingapp;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID accountnumber;
    private double balance;
    private int c_id;

    public UUID getAccountNumber() {
        return accountnumber;
    }

    public double getBalance() {
        return balance;
    }
    
    public int getCid() {
    	return c_id;
    }

    public Account(Customer c) {
        accountnumber = UUID.randomUUID();
        balance = 0.0;
        this.c_id = c.getCid();
        Main.badao.insertAccount(this);
    }
    
    public Account(UUID accountNumber, double balance, int c_id) {
    	this.accountnumber = accountNumber;
    	this.balance = balance;
    	this.c_id = c_id;
    }
    
    @Override
    public String toString() {
        return accountnumber.toString();
    }

    public void deposit(Double number) {
        balance += number;
        Main.badao.updateAccount(this);
    }

    public void withdrawal(Double number) {
        balance -= number;
        Main.badao.updateAccount(this);
    }

    public void checkBalance() {
        System.out.println(balance);
    }
    
    public void transfer(String accountNumber2, Double number) {
    	this.withdrawal(number);
    	Main.badao.Transfer(this.accountnumber.toString(), accountNumber2, number);
    }
}