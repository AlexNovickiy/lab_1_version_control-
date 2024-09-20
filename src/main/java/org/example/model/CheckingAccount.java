package org.example.model;

public class CheckingAccount extends BankAccount {
    private double dailyWithdrawalLimit;

    public CheckingAccount(String customerName, Long userId, String userPassword) {
        super(customerName, userId, userPassword);
        this.dailyWithdrawalLimit = 300;
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (this.getAccountBalance() >= amount && amount <= this.dailyWithdrawalLimit) {
            this.setAccountBalance(this.getAccountBalance() - amount);
        }
    }
}
