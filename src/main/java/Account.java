public abstract class Account {

    private final double APR;
    private double balance;
    private static int lastID = 10000000;
    private final String ID;

    Account(double APR, double balance){
        this.balance = balance;
        this.APR = APR;
        lastID += 1;
        ID = "" + lastID;
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


    public abstract String getType();

}
