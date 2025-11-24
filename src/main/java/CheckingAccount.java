public class CheckingAccount extends Account{

    CheckingAccount(String ID, double APR){
        super(ID, APR, 0);
    }


    @Override
    public String getType() {
        return "Checking";
    }
}
