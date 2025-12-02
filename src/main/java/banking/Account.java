package banking;

public abstract class Account {

    private final double APR;
    private double balance;
    private final String ID;

    Account(String ID, double APR, double balance){
        this.balance = balance;
        this.APR = APR;
        this.ID = ID;
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

    public abstract void accrueInterest(int months);

    public abstract boolean validateDeposit(double amount);
}
