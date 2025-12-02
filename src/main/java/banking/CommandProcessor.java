package banking;

import java.util.Map;

public class CommandProcessor {

    private final Bank BANK;

    public CommandProcessor(Bank bank) {
        this.BANK = bank;
    }

    public void process(String command) {
        String[] parsedCommand = command.split(" ");
        switch(parsedCommand[0]){
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
                }
                break;
            case "deposit":
                BANK.deposit(parsedCommand[1], Double.parseDouble(parsedCommand[2]));
                break;
            case "pass":
                for(Map.Entry<String, Account> entry : BANK.getAccounts().entrySet()){
                    double balance = entry.getValue().getBalance();
                    if(balance == 0){
                        BANK.getAccounts().remove(entry.getKey());
                    } else if (balance < 100) {
                        BANK.withdraw(entry.getValue().getID(), 25);
                    }
                }
        }

    }
}
