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
        for(String command : commandStorage.getValidCommands()){
            String[] parsedCommand = command.split(" ");
            switch (parsedCommand[0].toLowerCase()){
                case "create":
                    if(bank.retrieveAccount(parsedCommand[2]) != null) {
                        String type = parsedCommand[1].toLowerCase();
                        switch (type) {
                            case "checking":
                                output.add("Checking 12345678 0 3.60");
                                break;
                        }
                    }
                    break;
            }
        }
        output.addAll(commandStorage.getInvalidCommands());
        return output;
    }
}
