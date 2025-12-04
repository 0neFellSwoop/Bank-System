package banking;

public class WithdrawCommandValidator extends ArgumentValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validateCommandLength(parsedCommand, 3)){
            if(super.validateID(parsedCommand[1], bank) && super.validateAmount(parsedCommand[2])){
                return bank.retrieveAccount(parsedCommand[1]).validateWithdrawal(Double.parseDouble(parsedCommand[2]));            }
            return false;
        }
        return false;
    }
}
