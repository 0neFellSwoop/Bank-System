package banking;

import java.util.Iterator;
import java.util.Map;

public class CommandProcessor {

    private final Bank BANK;

    public CommandProcessor(Bank bank) {
        this.BANK = bank;
    }

    public void process(String command) {
        String[] parsedCommand = command.split(" ");
        switch(parsedCommand[0].toLowerCase()){
            case "create":
                String ID = parsedCommand[2];
                double APR = Double.parseDouble(parsedCommand[3]);
                switch(parsedCommand[1]){
                    case "checking":
                        BANK.addAccount(new CheckingAccount(ID, APR));
                        break;
                    case "savings":
                        BANK.addAccount(new SavingsAccount(ID, APR));
                        break;
                    case "cd":
                        BANK.addAccount(new CDAccount(ID, APR, Double.parseDouble(parsedCommand[4])));
                        break;
                    default:
                        break;
                }
                break;
            case "deposit":
                BANK.deposit(parsedCommand[1], Double.parseDouble(parsedCommand[2]));
                break;
            case "withdraw":
                BANK.withdraw(parsedCommand[1], Double.parseDouble(parsedCommand[2]));
                break;
            case "transfer":
                double amount = Double.parseDouble(parsedCommand[3]);
                String senderID = parsedCommand[1];
                String destinationID = parsedCommand[2];
                double senderBalance = BANK.retrieveAccount(senderID).getBalance();
                BANK.withdraw(senderID, amount);
                if(amount > senderBalance){
                    amount = senderBalance;
                }
                BANK.deposit(destinationID, amount);
                break;
            case "pass":
                Iterator<Map.Entry<String, Account>> iterator = BANK.getAccounts().entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, Account> account = iterator.next();
                    double balance = account.getValue().getBalance();
                    if(balance == 0){
                        iterator.remove();
                    } else if (balance < 100) {
                        BANK.withdraw(account.getValue().getID(), 25);
                    }
                    account.getValue().accrueInterest(Integer.parseInt(parsedCommand[1]));
                }
            default:
                break;


        }

    }
}
