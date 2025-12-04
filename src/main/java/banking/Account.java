package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class Account {

    private final double APR;
    private double balance;
    private final String ID;
    private int accountNumber = 0;

    Account(String ID, double APR, double balance){
        this.balance = balance;
        this.APR = APR;
        this.ID = ID;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getAPR() {
        return APR;
    }

    public void deposit(double depositAmount) {
        balance += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        balance -= withdrawAmount;
        if (balance < 0){
            balance = 0;
        }
    }

    public String getID() {
        return ID;
    }

    public void accrueInterest(int months){
        for(int i = 0; i < months; i++){
            this.deposit(balance * APR/100/12);
        }
    }

    @Override
    public String toString(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return ID + " " + decimalFormat.format(balance) + " " + decimalFormat.format(APR);
    }

    public abstract boolean validateDeposit(double amount);

    public abstract boolean validateWithdrawal(double amount);


}
