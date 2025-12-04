package banking;

public class CreateCommandValidator extends ArgumentValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(parsedCommand.length > 3){
            String ID = parsedCommand[2];
            double APR;
            try {
                Integer.parseInt(ID);
                APR = Double.parseDouble(parsedCommand[3]);
            } catch (NumberFormatException e) {
                return false;
            }
            if(ID.length() != 8 || super.validateID(ID, bank)){
                return false;
            }
            if(APR < 0 || APR > 10){
                return false;
            }
            String account = parsedCommand[1].toUpperCase();
            if(account.equals("CHECKING") || account.equals("SAVINGS")){
                return this.validateCheckingOrSavings(parsedCommand);
            }
            else if (account.equals("CD")){
                return this.validateCD(parsedCommand);
            }
        }
        return false;
    }

    private boolean validateCD(String[] parsedCommand) {
        if(super.validateCommandLength(parsedCommand, 5)){
            if(!super.validateAmount(parsedCommand[4])){
                return false;
            }
            double initial = Double.parseDouble(parsedCommand[4]);
            return !(initial < 1000) && !(initial > 10000);
        }
        return false;
    }

    private boolean validateCheckingOrSavings(String[] parsedCommand) {
        return super.validateCommandLength(parsedCommand, 4);
    }
}
