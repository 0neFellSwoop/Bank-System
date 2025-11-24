import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;
    Bank bank;

    @BeforeEach
    void setUp(){
        input = new ArrayList<>();
        bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
    }

    private void assertSingleCommand(String command, List<String> actual){
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid(){
        input.add("creat checking 12345678 1.6");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.6", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid(){
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid(){
        input.add("creat checking 12345678 1.6");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.6", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID(){
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("create checking 12345678 1.0", actual);
    }

    @Test
    void valid_to_create_account(){
        input.add("create cd 21345678 1.0 3000");

        List<String> actual = masterControl.start(input);

        assertEquals(3000, bank.retrieveAccount("21345678").getBalance());
    }

    @Test
    void valid_to_create_accounts_with_different_ID(){
        input.add("create checking 12345678 1.0");
        input.add("create savings 21345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void invalid_to_deposit_to_nonexistant_account(){
        input.add("deposit 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 12345678 100", actual);
    }

    @Test
    void valid_to_deposit_to_existing_account(){
        input.add("create savings 12345678 1.0");
        input.add("deposit 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(100, bank.retrieveAccount("12345678").getBalance());
    }



}
