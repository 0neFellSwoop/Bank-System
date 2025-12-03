package banking;

public class CDAccount extends Account {

    public CDAccount(String ID, double APR, double balance) {
        super(ID, APR, balance);
    }

    @Override
    public void accrueInterest(int months) {
        for(int i = 0; i < months*4; i++){
            super.deposit(super.getBalance() * super.getAPR()/100/12);
        }
    }

    @Override
    public boolean validateDeposit(double amount) {
        return false;
    }

    @Override
    public boolean validateWithdrawal(double amount) {
        return false;
    }


}
