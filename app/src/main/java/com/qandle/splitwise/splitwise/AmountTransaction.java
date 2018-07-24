package com.qandle.splitwise.splitwise;

public class AmountTransaction {
    int payee,receiver;
    double amount;

    public AmountTransaction(int payee, int receiver, double amount) {
        this.payee = payee;
        this.receiver = receiver;
        this.amount = amount;
    }

    public int getPayee() {
        return payee;
    }

    public int getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void addAmount(double amount)
    {
        this.amount = this.amount + amount;
    }
}
