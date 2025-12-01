package banking;

public class CDAccount extends Account {

    public CDAccount(String ID, double APR, double balance) {
        super(ID, APR, balance);
    }

    @Override
    public String getType() {
        return "CD";
    }


}
