package banking;

public class DepositCommandValidator extends ArgumentValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validateCommandLength(parsedCommand, 3)){
            if(super.validateID(parsedCommand[1], bank) && super.validateAmount(parsedCommand[2])){
                double amount = Double.parseDouble(parsedCommand[2]);
                if(!(amount < 0) && bank.retrieveAccount(parsedCommand[1]).validateDeposit(amount)){
                    super.logCommand(parsedCommand, bank);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
