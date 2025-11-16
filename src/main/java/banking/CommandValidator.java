package banking;

public class CommandValidator {

    private final CreateCommandValidator CREATE_VALIDATOR;
    private final DepositCommandValidator DEPOSIT_VALIDATOR;
    private final Bank BANK;

    public CommandValidator(Bank bank) {
        this.BANK = bank;
        CREATE_VALIDATOR = new CreateCommandValidator();
        DEPOSIT_VALIDATOR = new DepositCommandValidator();
    }

    public boolean validate(String command) {
        if(command == null){
            return false;
        }
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0].toLowerCase();
        if(type.equals("create")){
            return CREATE_VALIDATOR.validate(parsedCommand, BANK);
        }
        else if(type.equals("deposit")){
            return DEPOSIT_VALIDATOR.validate(parsedCommand, BANK);
        }
        return false;
    }
}
