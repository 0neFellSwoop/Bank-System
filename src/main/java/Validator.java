public class Validator {
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0];
        if(!(type.equals("create") || type.equals("deposit"))){
            return false;
        }
        String ID = parsedCommand[2];
        return ID.length() == 8;
    }
}
