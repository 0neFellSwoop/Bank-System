public class DepositValidator {


    public boolean validate(String[] parsedCommand, Bank bank) {
        double amount;
        try{
            amount = Double.parseDouble(parsedCommand[2]);
        } catch (NumberFormatException e){
            return false;
        }
        if(amount < 0){
            return false;
        }
        return true;
    }
}
