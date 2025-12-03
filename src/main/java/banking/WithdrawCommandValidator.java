package banking;

public class WithdrawCommandValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(parsedCommand.length != 3){
            return false;
        }
        String ID = parsedCommand[1];
        if(bank.retrieveAccount(ID) == null){
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
        return true;
    }
}
