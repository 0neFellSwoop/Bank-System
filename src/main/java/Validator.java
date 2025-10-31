public class Validator {



    public Validator() {
    }

    public boolean validate(String command) {
        if(command == null){
            return false;
        }
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0];
        if(!(type.equals("create") || type.equals("deposit"))) {
            return false;
        }
        if(type.equals("create")){
            return CreateValidator.validate(parsedCommand);
        }
        return true;
    }
}
