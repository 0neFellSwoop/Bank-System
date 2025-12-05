package banking;

public class TransferCommandValidator extends ArgumentValidator {

    @Override
    public void logCommand(String[] parsedCommand, Bank bank){
        super.logCommand(parsedCommand, bank);
        bank.retrieveAccount(parsedCommand[2]).logCommand(String.join(" ", parsedCommand));
    }

    public boolean validate(String[] parsedCommand, Bank bank) {
        if(super.validateCommandLength(parsedCommand, 4)){
            String senderID = parsedCommand[1];
            String destinationID = parsedCommand[2];
            if(!(super.validateID(senderID, bank) && super.validateID(destinationID, bank)) || !super.validateAmount(parsedCommand[3])){
                return false;
            }
            double amount = Double.parseDouble(parsedCommand[3]);
            if(!(amount < 0)){
                if(bank.validateTransfer(senderID, destinationID, amount)){
                    this.logCommand(parsedCommand, bank);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

}
