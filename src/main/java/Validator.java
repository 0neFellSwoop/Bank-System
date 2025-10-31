public class Validator {

    private final CreateValidator CREATE_VALIDATOR;
    private final DepositValidator DEPOSIT_VALIDATOR;
    private Bank BANK;

    public Validator(Bank bank) {
        this.BANK = bank;
        CREATE_VALIDATOR = new CreateValidator();
        DEPOSIT_VALIDATOR = new DepositValidator();
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
