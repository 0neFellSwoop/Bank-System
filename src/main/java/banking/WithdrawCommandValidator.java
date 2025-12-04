package banking;

public class WithdrawCommandValidator extends CashFlowValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validate(parsedCommand, bank)){
            return bank.retrieveAccount(parsedCommand[1]).validateWithdrawal(Double.parseDouble(parsedCommand[2]));
        }
        return false;
    }
}
