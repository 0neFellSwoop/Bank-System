package banking;

public class SavingsAccount extends Account{

    SavingsAccount(String ID, double APR){
        super(ID, APR, 0);
    }

    @Override
    public void accrueInterest(int months) {
        for(int i = 0; i < months; i++){
            super.deposit(super.getBalance() * super.getAPR()/100/12);
        }
    }

    @Override
    public boolean validateDeposit(double amount) {
        return !(amount > 2500);
    }
}
