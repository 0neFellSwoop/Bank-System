public class CreateValidator {
    public boolean validate(String[] parsedCommand) {
        if(parsedCommand.length < 4){
            return false;
        }
        String account = parsedCommand[1].toUpperCase();
        if(!(account.equals("CHECKING") || account.equals("SAVINGS") || account.equals("CD"))){
            return false;
        }
        return true;
    }
}
