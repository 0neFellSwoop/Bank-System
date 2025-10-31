import java.util.ArrayList;

public class CreateValidator {

    private final ArrayList<String> IDs = new ArrayList<>();

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
        if(ID.length() != 8 || IDs.contains(ID)){
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
            return this.validateCheckingOrSavings(parsedCommand);
        }
        else if (account.equals("CD")){
            return this.validateCD(parsedCommand);
        }
        return false;
    }

    private boolean validateCD(String[] parsedCommand) {
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
        IDs.add(parsedCommand[2]);
        return true;
    }

    private boolean validateCheckingOrSavings(String[] parsedCommand) {
        if(parsedCommand.length != 4){
            return false;
        }
        IDs.add(parsedCommand[2]);
        return true;
    }
}
