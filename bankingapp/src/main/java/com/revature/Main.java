package com.revature;

import java.io.*;
import java.util.Scanner;

public class Main {
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

    public static void existingCustomer(Customer customer) {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        String input;
        Account account = customer.displayAccountNumber(0);
        double number;
        do {
            System.out.println("What would you like to do?");
            System.out.println("d for deposit, w for withdrawal, c to check balance, q if you would like to exit");
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
                case "c":
                case "C":
                    account.checkBalance();
                    break;
                case "q":
                case "Q":
                    System.out.println("Goodbye");
                    quit = true;
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customer.displayAccountNumber(0).toString() + ".txt"))) {
                        oos.writeObject(account);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        } while (!quit);
        sc.close();
    }

    public static void existingCustomer() {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        String input, filename;
        boolean escape = true;
        Account q = null;
        double number;
        do {
            System.out.println("Please enter your account number");
            input = sc.nextLine();
            filename = input + ".txt";
            if (input != "quit") {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                    q = (Account)ois.readObject();
                    System.out.println(q);
                    ois.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("Sorry, no account was found. Please try again or type quit to exit.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else {
                escape = false;
            }
        } while(q == null && escape);
        do {
            System.out.println("What would you like to do?");
            System.out.println("d for deposit, w for withdrawal, c to check balance, q if you would like to exit");
            input = sc.nextLine();
            switch (input) {
            case "d":
            case "D":
                System.out.println("How much?");
                number = sc.nextDouble();
                sc.nextLine();
                q.deposit(number);
                break;
            case "w":
            case "W":
                System.out.println("How much?");
                number = sc.nextDouble();
                sc.nextLine();
                q.withdrawal(number);
                break;
            case "c":
            case "C":
                q.checkBalance();
                break;
            case "q":
            case "Q":
                System.out.println("Goodbye");
                quit = true;
                if (q != null) {
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                        oos.writeObject(q);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            }
        } while (!quit);
        sc.close();
    }
}