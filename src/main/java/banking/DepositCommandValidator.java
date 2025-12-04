package banking;

public class DepositCommandValidator extends CashFlowValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validate(parsedCommand, bank)){
            double amount = Double.parseDouble(parsedCommand[2]);
            return !(amount < 0) && bank.retrieveAccount(parsedCommand[1]).validateDeposit(amount);
        }
        return false;
    }
}
