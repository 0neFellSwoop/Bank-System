public class CreateValidator {


    public boolean validate(String[] parsedCommand) {
        if(parsedCommand.length < 4){
            return false;
        }

        String ID = parsedCommand[2];
        try {
            Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            return false;
        }
        if(ID.length() != 8){
            return false;
        }
        double APR;
        try {
            APR = Double.parseDouble(parsedCommand[3]);
        } catch (NumberFormatException e) {
            return false;
        }
        if(APR < 0 || APR > 10){
            return false;
        }
        String account = parsedCommand[1].toUpperCase();
        if(account.equals("CHECKING") || account.equals("SAVINGS")){
            return CreateValidator.validateCheckingOrSavings(parsedCommand);
        }
        else if (account.equals("CD")){
            return CreateValidator.validateCD(parsedCommand);
        }
        return false;
    }

    private static boolean validateCD(String[] parsedCommand) {
        if(parsedCommand.length != 5){
            return false;
        }
        double initial;
        try {
            initial = Double.parseDouble(parsedCommand[4]);
        } catch (NumberFormatException e) {
            return false;
        }
        if(initial < 1000 || initial > 10000){
            return false;
        }
        return true;
    }

    private static boolean validateCheckingOrSavings(String[] parsedCommand) {
        return true;
    }
}
