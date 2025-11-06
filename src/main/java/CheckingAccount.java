public class CheckingAccount extends Account{

    CheckingAccount(double APR){
        super(APR, 0);
    }


    @Override
    public String getType() {
        return "Checking";
    }
}
