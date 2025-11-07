public class CommandProcessor {

    private final Bank BANK;

    public CommandProcessor(Bank bank) {
        this.BANK = bank;
    }

    public void process(String command) {
        String[] parsedCommand = command.split(" ");
        switch(parsedCommand[0]){
            case "create":
                switch(parsedCommand[1]){
                    case "checking":
                        BANK.addAccount(new CheckingAccount(parsedCommand[2], Double.parseDouble(parsedCommand[3])));
                        break;
                    case "savings":
                        break;
                    case "cd":
                        break;
                }
                break;
            case "deposit":
                break;
        }

    }
}
