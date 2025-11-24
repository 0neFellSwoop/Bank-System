import java.util.List;

public class MasterControl {

    private final CommandProcessor commandProcessor;
    private final CommandValidator commandValidator;
    private final CommandStorage commandStorage;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.commandValidator = commandValidator;
    }

    public List<String> start(List<String> input) {
        for(String command : input){
            if(commandValidator.validate(command)){
                commandProcessor.process(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return commandStorage.getInvalidCommands();
    }
}
