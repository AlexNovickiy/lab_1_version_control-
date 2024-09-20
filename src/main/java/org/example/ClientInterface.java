package org.example;

import org.example.model.BankAccount;
import org.example.model.CheckingAccount;
import org.example.exceptions.AccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private final List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() {
        String option;
        while (true) {
            printMainMenu();
            option = scanner.next();

            switch (Integer.parseInt(option)) {
                case 1:
                    try {
                        addNewAccount();
                    } catch (AccountException e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 2:
                    try {
                        deleteAccount();
                    } catch (AccountException e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 3:
                    try {
                        makeDeposit();
                    } catch (AccountException e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 4:
                    try {
                        makeWithdrawal();
                    } catch (AccountException e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 5:
                    try {
                        printAccountInfo();
                    } catch (AccountException e) {
                        System.err.println("Error: " + e.getMessage() + "\n");
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Please enter a valid choice (1 thru 6)");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private void addNewAccount() throws AccountException {
        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        String name = scanner.next();

        // Перевірка унікальності userId
        String userId;
        do {
            System.out.print(">>> Enter user id: ");
            userId = scanner.next();
        } while (!isUserIdUnique(userId));

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();
        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();

        if (!password.equals(confirmPassword)) {
            throw new AccountException("Passwords do not match.");
        }

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            throw new AccountException("Password must be at least 8 characters and contain at least one digit.");
        }

        BankAccount newAccount = new CheckingAccount(name, Long.parseLong(userId), password);
        accounts.add(newAccount);
        System.out.println("Account successfully created!");
    }

    // Перевірка на унікальність userId
    private boolean isUserIdUnique(String userId) {
        for (BankAccount account : accounts) {
            if (account.getUserId().toString().equals(userId)) {
                System.err.println("User ID already exists. Please choose another one.");
                return false;
            }
        }
        return true;
    }

    private void deleteAccount() throws AccountException {
        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount accountToDelete = findAccountByUserId(userId);
        if (accountToDelete == null) {
            throw new AccountException("Account not found.");
        }

        if (!accountToDelete.getUserPassword().equals(password)) {
            throw new AccountException("Incorrect password for the account.");
        }

        if (accountToDelete.getAccountBalance() > 0) {
            throw new AccountException("Cannot delete account with a non-zero balance.");
        }

        accounts.remove(accountToDelete);
        System.out.println("Account successfully deleted!");
    }

    private void makeDeposit() throws AccountException {
        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.nextDouble();

        BankAccount account = findAccountByUserId(userId);
        if (account == null) {
            throw new AccountException("Account not found.");
        }

        if (!account.getUserPassword().equals(password)) {
            throw new AccountException("Incorrect password for the account.");
        }

        if (amount <= 0) {
            throw new AccountException("Deposit amount should be greater than 0.");
        }

        account.deposit(amount);
        System.out.println("Deposit successful! New balance: " + account.getAccountBalance());
    }

    private void makeWithdrawal() throws AccountException {
        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.nextDouble();

        BankAccount account = findAccountByUserId(userId);
        if (account == null) {
            throw new AccountException("Account not found.");
        }

        if (!account.getUserPassword().equals(password)) {
            throw new AccountException("Incorrect password for the account.");
        }

        if (amount <= 0) {
            throw new AccountException("Withdrawal amount should be greater than 0.");
        }

        if (amount > account.getAccountBalance()) {
            throw new AccountException("Insufficient funds.");
        }

        if (account instanceof CheckingAccount && amount > ((CheckingAccount) account).getDailyWithdrawalLimit()) {
            throw new AccountException("Withdrawal amount exceeds daily limit.");
        }

        account.withdraw(amount);
        System.out.println("Withdrawal successful! New balance: " + account.getAccountBalance());
    }

    private void printAccountInfo() throws AccountException {
        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount account = findAccountByUserId(userId);
        if (account == null) {
            throw new AccountException("Account not found.");
        }

        if (!account.getUserPassword().equals(password)) {
            throw new AccountException("Incorrect password for the account.");
        }

        System.out.println("\nAccount info: ");
        System.out.println(" - Customer name: " + account.getCustomerName());
        System.out.println(" - Account balance: " + account.getAccountBalance());
        System.out.println(" - Account type: Checking");

        if (account instanceof CheckingAccount) {
            System.out.println(" - Account daily withdrawal limit: " + ((CheckingAccount) account).getDailyWithdrawalLimit());
        }

        System.out.print("\nPress Enter to return to the main menu...");
        scanner.nextLine(); // Clear the buffer
        scanner.nextLine(); // Wait for Enter key press
    }

    private BankAccount findAccountByUserId(String userId) {
        for (BankAccount account : accounts) {
            if (account.getUserId().toString().equals(userId)) {
                return account;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ClientInterface clientInterface = new ClientInterface();
        clientInterface.start();
    }
}
