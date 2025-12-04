package banking;


import java.util.ArrayList;
import java.util.List;

public class MasterControl {

    private final CommandProcessor commandProcessor;
    private final CommandValidator commandValidator;
    private final CommandStorage commandStorage;
    private final Bank bank;

    public MasterControl(Bank bank) {
        this.commandProcessor = new CommandProcessor(bank);
        this.commandStorage = new CommandStorage();
        this.commandValidator = new CommandValidator(bank);
        this.bank = bank;
    }

    public List<String> start(List<String> input) {
        for(String command : input){
            if(commandValidator.validate(command)){
                commandProcessor.process(command);
                commandStorage.addValidCommand(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return output();
    }

    private List<String> output(){
        List<String> output = new ArrayList<>();
        int newAccounts = 0;
        Account account;
        String[] parsedCommand;
        String ID;
        for(String command : commandStorage.getValidCommands()){
            parsedCommand = command.split(" ");
            switch (parsedCommand[0].toLowerCase()){
                case "create":
                    newAccounts++;
                    ID = parsedCommand[2];
                    if(bank.retrieveAccount(ID) != null) {
                        account = bank.retrieveAccount(ID);
                        if(newAccounts == account.getAccountNumber()){
                            output.add(account.toString());
                        }
                    }
                    break;
            }
        }
        output.addAll(commandStorage.getInvalidCommands());
        return output;
    }
}
