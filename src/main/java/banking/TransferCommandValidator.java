package banking;

public class TransferCommandValidator extends ArgumentValidator {

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validateCommandLength(parsedCommand, 4)){
            String senderID = parsedCommand[1];
            String destinationID = parsedCommand[2];
            if(!(super.validateID(senderID, bank) && super.validateID(destinationID, bank)) || !super.validateAmount(parsedCommand[3])){
                return false;
            }
            double amount = Double.parseDouble(parsedCommand[3]);
            if(!(amount < 0)){
                return bank.validateTransfer(senderID, destinationID, amount);
            }
            return false;
        }
        return false;
    }

}
