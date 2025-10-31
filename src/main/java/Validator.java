public class Validator {

    private final CreateValidator CREATE_VALIDATOR;

    public Validator() {
        CREATE_VALIDATOR = new CreateValidator();
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
            return CREATE_VALIDATOR.validate(parsedCommand);
        }
        return true;
    }
}
