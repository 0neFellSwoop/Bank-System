package banking;

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
        masterControl = new MasterControl(bank);
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

        assertEquals("create checking 12345678 1.0", actual.get(1));
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
        input.add("create checking 21345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
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

    @Test
    void invalid_commands_come_after_valid_commands(){
        input.add("create checking 12345678 3.6");
        input.add("womp");

        List<String> actual = masterControl.start(input);

        assertEquals("womp", actual.get(1));
    }

    @Test
    void output_created_account(){
        input.add("create checking 12345678 3.6");

        List<String> actual = masterControl.start(input);

        assertEquals("Checking 12345678 0.00 3.60", actual.get(0));
    }

    @Test
    void output_created_savings_account(){
        input.add("create savings 12345678 5");

        List<String> actual = masterControl.start(input);

        assertEquals("Savings 12345678 0.00 5.00", actual.get(0));
    }

    @Test
    void output_created_CD(){
        input.add("create cd 12345678 10 1000");

        List<String> actual = masterControl.start(input);

        assertEquals("Cd 12345678 1000.00 10.00", actual.get(0));
    }

    @Test
    void dont_print_deleted_account(){
        input.add("create savings 12345678 5");
        input.add("pass 1");
        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void print_correct_account_if_deleted_account_has_same_ID(){
        input.add("create savings 12345678 5");
        input.add("pass 1");
        input.add("create cd 12345678 10 1000");

        List<String> actual = masterControl.start(input);

        assertEquals("Cd 12345678 1000.00 10.00", actual.get(0));
    }

    @Test
    void output_accounts_in_correct_order_if_deleted_account_has_same_ID(){
        input.add("create savings 12345678 5");
        input.add("pass 1");
        input.add("create savings 87654321 5");
        input.add("create cd 12345678 10 1000");

        List<String> actual = masterControl.start(input);

        assertEquals("Cd 12345678 1000.00 10.00", actual.get(1));

    }

}
