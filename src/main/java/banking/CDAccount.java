package banking;

public class CDAccount extends Account {

    private int accountAge;

    public CDAccount(String ID, double APR, double balance) {
        super(ID, APR, balance);
        accountAge = 0;
    }

    @Override
    public void accrueInterest(int months) {
        accountAge += months;
        super.accrueInterest(months*4);
    }

    @Override
    public boolean validateDeposit(double amount) {
        return false;
    }

    @Override
    public boolean validateWithdrawal(double amount) {
        return !(accountAge < 12) && amount >= super.getBalance();
    }


}
