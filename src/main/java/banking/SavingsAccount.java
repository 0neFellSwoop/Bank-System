package banking;

public class SavingsAccount extends Account{

    private boolean monthlyWithdraw;

    SavingsAccount(String ID, double APR){
        super(ID, APR, 0);
        monthlyWithdraw = false;
    }

    @Override
    public void accrueInterest(int months) {
        monthlyWithdraw = false;
        for(int i = 0; i < months; i++){
            super.deposit(super.getBalance() * super.getAPR()/100/12);
        }
    }

    @Override
    public boolean validateDeposit(double amount) {
        return !(amount > 2500);
    }

    @Override
    public boolean validateWithdrawal(double amount) {
        return !(amount < 0 || amount > 1000) && !monthlyWithdraw;
    }

    @Override
    public void withdraw(double amount){
        monthlyWithdraw = true;
        super.withdraw(amount);
    }
}
