package banking;

public class CommandValidator {

    private final CreateCommandValidator CREATE_VALIDATOR;
    private final DepositCommandValidator DEPOSIT_VALIDATOR;
    private final PassTimeCommandValidator PASS_TIME_VALIDATOR;
    private final Bank BANK;

    public CommandValidator(Bank bank) {
        this.BANK = bank;
        CREATE_VALIDATOR = new CreateCommandValidator();
        DEPOSIT_VALIDATOR = new DepositCommandValidator();
        PASS_TIME_VALIDATOR = new PassTimeCommandValidator();
    }

    public boolean validate(String command) {
        if(command == null){
            return false;
        }
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0].toLowerCase();
        return switch (type) {
            case "create" -> CREATE_VALIDATOR.validate(parsedCommand, BANK);
            case "deposit" -> DEPOSIT_VALIDATOR.validate(parsedCommand, BANK);
            case "pass" -> PASS_TIME_VALIDATOR.validate(parsedCommand);
            default -> false;
        };
    }
}
