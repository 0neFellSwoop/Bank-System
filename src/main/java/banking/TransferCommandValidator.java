package banking;

public class TransferCommandValidator extends CashFlowValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(parsedCommand.length != 4){
            return false;
        }
        if(!(super.validateID(parsedCommand[1], bank) && super.validateID(parsedCommand[2], bank)) || !super.validateAmount(parsedCommand[3])){
            return false;
        }
        double amount = Double.parseDouble(parsedCommand[3]);
        return !(amount < 0);
    }
}
