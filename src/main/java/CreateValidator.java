public class CreateValidator {


    public static boolean validate(String[] parsedCommand) {
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
        Double APR;
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
            return CreateValidator.validateCheckingOrSavings();
        }
        else if (account.equals("CD")){
            return CreateValidator.validateCD();
        }
        return false;
    }

    private static boolean validateCD() {
        return true;
    }

    private static boolean validateCheckingOrSavings() {
        return true;
    }
}
