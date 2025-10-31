public class Validator {
    public boolean validate(String command) {
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0];
        return (type.equals("create") || type.equals("deposit"));
    }
}
