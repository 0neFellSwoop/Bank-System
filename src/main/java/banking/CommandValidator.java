package banking;

public class CommandValidator {

    private final CreateCommandValidator CREATE_VALIDATOR;
    private final DepositCommandValidator DEPOSIT_VALIDATOR;
    private final PassTimeCommandValidator PASS_TIME_VALIDATOR;
    private final WithdrawCommandValidator WITHDRAW_VALIDATOR;
    private final TransferCommandValidator TRANSFER_VALIDATOR;
    private final Bank BANK;

    public CommandValidator(Bank bank) {
        this.BANK = bank;
        this.CREATE_VALIDATOR = new CreateCommandValidator();
        this.DEPOSIT_VALIDATOR = new DepositCommandValidator();
        this.PASS_TIME_VALIDATOR = new PassTimeCommandValidator();
        this.WITHDRAW_VALIDATOR = new WithdrawCommandValidator();
        this.TRANSFER_VALIDATOR = new TransferCommandValidator();
    }

    public boolean validate(String command) {
        if(command == null){
            return false;
        }
        String[] parsedCommand = command.split(" ");
        String type = parsedCommand[0].toLowerCase();
        switch (type) {
            case "create":
                return CREATE_VALIDATOR.validate(parsedCommand, BANK);
            case "deposit":
                return DEPOSIT_VALIDATOR.validate(parsedCommand, BANK);
            case "withdraw":
                return WITHDRAW_VALIDATOR.validate(parsedCommand, BANK);
            case "transfer":
                return TRANSFER_VALIDATOR.validate(parsedCommand, BANK);
            case "pass":
                return PASS_TIME_VALIDATOR.validate(parsedCommand);

            default:
                return false;
        }
    }
}
