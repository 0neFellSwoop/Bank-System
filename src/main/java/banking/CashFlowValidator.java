package banking;

public abstract class CashFlowValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if (parsedCommand.length != 3) {
            return false;
        }
        if(!validateID(parsedCommand[1], bank)){
            return false;
        }
        return validateAmount(parsedCommand[2]);
    }

    public boolean validateID(String ID, Bank bank){
        return !(bank.retrieveAccount(ID) == null);
    }

    public boolean validateAmount(String amount){
        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
