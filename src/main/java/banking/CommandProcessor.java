package banking;

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
        }

    }
}
