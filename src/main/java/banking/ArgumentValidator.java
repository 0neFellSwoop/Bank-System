package banking;

public abstract class ArgumentValidator {

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

    public boolean validateCommandLength(String[] parsedCommand, int length){
        return parsedCommand.length == length;
    }
}
