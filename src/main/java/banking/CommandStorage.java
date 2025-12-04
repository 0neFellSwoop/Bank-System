package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

    private final List<String> invalidCommands = new ArrayList<>();
    private final List<String> validCommands = new ArrayList<>();

    public void addInvalidCommand(String command) {
        invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

    public void addValidCommand(String command) {
        validCommands.add(command);
    }

    public List<String> getValidCommands() {
        return validCommands;
    }
}
