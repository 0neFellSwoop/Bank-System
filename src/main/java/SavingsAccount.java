public class SavingsAccount extends Account{

    SavingsAccount(double APR){
        super(APR, 0);
    }


    @Override
    public String getType() {
        return "Savings";
    }
}
