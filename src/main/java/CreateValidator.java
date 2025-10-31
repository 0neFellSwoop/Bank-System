public class CreateValidator {
    public boolean validate(String[] parsedCommand) {
        if(parsedCommand.length < 4){
            return false;
        }
        return true;
    }
}
