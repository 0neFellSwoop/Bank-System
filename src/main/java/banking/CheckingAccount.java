package banking;

public class CheckingAccount extends Account{

    CheckingAccount(String ID, double APR){
        super(ID, APR, 0);
    }

    @Override
    public boolean validateDeposit(double amount) {
        return !(amount > 1000);
    }

    @Override
    public boolean validateWithdrawal(double amount) {
        return !(amount < 0 || amount > 400);
    }

}
