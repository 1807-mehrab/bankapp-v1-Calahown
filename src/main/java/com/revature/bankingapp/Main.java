package com.revature.bankingapp;

import java.util.Scanner;

public class Main {
	public static BAppDao badao = new BAppDao();
    public static void main(String[] args) {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        String input;
        while (!quit) {
            System.out.println("Hello, are you an existing customer? (y/n)");
            System.out.println("Type quit to exit at anytime.");
            input = sc.nextLine();
            if (input.equals("y") || input.equals("Y")) {
                existingCustomer();
                quit = true;
            } else if (input.equals("n") || input.equals("N")) {
                System.out.println("Would you like to create an account? (y/n)");
                input = sc.nextLine();
                if (input.equals("y") || input.equals("Y")) {
                    System.out.println("What name would you like under the account?");
                    input = sc.nextLine();
                    Customer customer = new Customer(input);
                    System.out.println("Your account has been created Mr. " + input);
                    System.out.println(customer.displayAccountNumber(0));
                    existingCustomer(customer);
                    quit = true;
                } else if (input.equals("n") || input.equals("N") || input.equals("quit")) {
                    System.out.println("goodbye");
                    quit = true;
                }
            } else if(input.equals("quit")) {
                System.out.println("goodbye");
                quit = true;
            } else {
                System.out.println("Sorry, I do not understand.");
            }
        }
        sc.close();
    }
    //is called when the customer does not exist
    public static void existingCustomer(Customer customer) {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        String input;
        Account account = customer.displayAccountNumber(0);
        double number;
        do {
            System.out.println("What would you like to do?");
            System.out.println("d for deposit, w for withdrawal, c to check balance, t to transfer money, q if you would like to exit");
            input = sc.nextLine();
            switch(input) {
                case "d":
                case "D":
                    System.out.println("How much?");
                    number = sc.nextDouble();
                    sc.nextLine();
                    account.deposit(number);
                    break;
                case "w":
                case "W":
                    System.out.println("How much?");
                    number = sc.nextDouble();
                    sc.nextLine();
                    account.withdrawal(number);
                    break;
                case "t":
                case "T": System.out.println("Please enter recipient's account");
                	Account b = null;
                	boolean quit2 = false;
                	while(b == null && !quit2) {
                		input = sc.nextLine();
                		if (input.equals("quit")) { break;}
                		b = badao.getAccount(input);
                		if (b == null) {
                			System.out.println("Sorry account not found, try again or type quit");
                		}
                	}
                	System.out.println("How Much?");
                	number = sc.nextDouble();
                	sc.nextLine();
                	account.transfer(b.getAccountNumber().toString(), number);
                	break;
                case "c":
                case "C":
                    account.checkBalance();
                    break;
                case "q":
                case "Q":
                    System.out.println("Goodbye");
                    quit = true;
                    badao.updateAccount(account);
            }
        } while (!quit);
        sc.close();
    }

    //is called when there is an existing customer
    public static void existingCustomer() {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        String input, accountnumber;
        boolean escape = true;
        Account a = null;
        double number;
        do {
            System.out.println("Please enter your account number");
            input = sc.nextLine();
            accountnumber = input;
            if (input != "quit") {
                a = badao.getAccount(accountnumber);
            } else {
                escape = false;
            }
        } while(a == null && escape);
        do {
            System.out.println("What would you like to do?");
            System.out.println("d for deposit, w for withdrawal, c to check balance, t to transfer money, q if you would like to exit");
            input = sc.nextLine();
            switch (input) {
            case "d":
            case "D":
                System.out.println("How much?");
                number = sc.nextDouble();
                sc.nextLine();
                a.deposit(number);
                break;
            case "w":
            case "W":
                System.out.println("How much?");
                number = sc.nextDouble();
                sc.nextLine();
                a.withdrawal(number);
                break;
            case "c":
            case "C":
                a.checkBalance();
                break;
            case "t":
            case "T": System.out.println("Please enter recipient's account");
            	Account b = null;
            	boolean quit2 = false;
            	while(b == null && !quit2) {
            		input = sc.nextLine();
            		if (input.equals("quit")) { break;}
            		b = badao.getAccount(input);
            		if (b == null) {
            			System.out.println("Sorry account not found, try again or type quit");
            		}
            	}
            	System.out.println("How Much?");
            	number = sc.nextDouble();
            	sc.nextLine();
            	a.transfer(b.getAccountNumber().toString(), number);
            	break;
            case "q":
            case "Q":
                System.out.println("Goodbye");
                quit = true;
                if (a != null) {
                    badao.updateAccount(a);
                }
                break;
            }
        } while (!quit);
        sc.close();
    }
}