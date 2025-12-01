package banking;

public class DepositCommandValidator {


    public boolean validate(String[] parsedCommand, Bank bank) {
        if(parsedCommand.length != 3){
            return false;
        }
        String ID = parsedCommand[1];
        if(bank.retrieveAccount(ID) == null || bank.retrieveAccount(ID).getType().equals("CD")){
            return false;
        }
        double amount;
        try{
            amount = Double.parseDouble(parsedCommand[2]);
        } catch (NumberFormatException e){
            return false;
        }
        if(amount < 0) {
            return false;
        }
        if(bank.retrieveAccount(ID).getType().equals("Savings")){
            return this.validateSavingsDeposit(amount);
        }
        else if(bank.retrieveAccount(ID).getType().equals("Checking")) {
            return this.validateCheckingDeposit(amount);
        } else { return false; }
    }

    private boolean validateCheckingDeposit(double amount) {
        return !(amount > 1000);
    }

    private boolean validateSavingsDeposit(double amount) {
        return !(amount > 2500);
    }
}
