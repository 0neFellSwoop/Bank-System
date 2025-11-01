public class CDAccount extends Account {

    public CDAccount(double APR, double balance) {
        super(APR, balance);
    }

    @Override
    public String getType() {
        return "CD";
    }


}
