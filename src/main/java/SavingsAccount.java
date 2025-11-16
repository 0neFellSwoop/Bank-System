public class SavingsAccount extends Account{

    SavingsAccount(String ID, double APR){
        super(ID, APR, 0);
    }


    @Override
    public String getType() {
        return "Savings";
    }
}
