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

    @Test
    void deposit_into_account_produces_correct_output(){
        input.add("Create checking 12345678 0.6");
        input.add("Deposit 12345678 700");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
    }


    @Test
    void invalid_deposit_is_placed_at_end(){
        input.add("Create checking 12345678 0.6");
        input.add("Deposit 12345678 5000");
        input.add("Deposit 12345678 700");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Deposit 12345678 5000", actual.get(2));

    }


    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }


    @Test
    void transfer_from_one_account_to_another_produces_correct_output(){
        input.add("Create checking 12345678 0.6");
        input.add("Create checking 87654321 0.6");
        input.add("deposit 12345678 500");
        input.add("deposit 87654321 99");
        input.add("transfer 12345678 87654321 200");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 12345678 300.00 0.60", actual.get(0));
        assertEquals("deposit 12345678 500", actual.get(1));
        assertEquals("transfer 12345678 87654321 200", actual.get(2));
        assertEquals("Checking 87654321 299.00 0.60", actual.get(3));
        assertEquals("deposit 87654321 99", actual.get(4));
        assertEquals("transfer 12345678 87654321 200", actual.get(5));

    }

    @Test
    void mature_CD_withdrawal(){
        input.add("Create cD 99999999 10 1200");
        input.add("pass 1");
        input.add("pass 65");
        input.add("Create savings 77777777 .4");
        input.add("deposit 77777777 500");
        input.add("Create savings 88888888 3.6");
        input.add("transfer 77777777 88888888 700");
        input.add("pass 11");
        input.add("withdraw 99999999 3000");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Cd 99999999 0.00 10.00", actual.get(0));
        assertEquals("withdraw 99999999 3000", actual.get(1));
        assertEquals("Savings 88888888 516.74 3.60", actual.get(2));
        assertEquals("transfer 77777777 88888888 700", actual.get(3));
        assertEquals("pass 65", actual.get(4));
    }

    @Test
    void catch_invalid_withdrawals_in_output(){
        input.add("Create cD 99999999 10 1200");
        input.add("withdraw 99999999 3000"); //invalid
        input.add("Create savings 77777777 3.4");
        input.add("withdraw 77777777 1001"); //invalid
        input.add("withdraw 77777777 30");
        input.add("withdraw 77777777 69"); //invalid
        input.add("Create checking 88888888 .1");
        input.add("withdraw 88888888 0");
        input.add("withdraw 88888888 400");
        input.add("withdraw 88888888 401"); //invalid
        input.add("withdraw 88888888 -1"); //invalid
        input.add("withdraw 88888888 abc"); //invalid
        input.add("withdraw 20 2"); //invalid
        input.add("withdraw 2"); //invalid
        input.add("withdraw"); //invalid

        List<String> actual = masterControl.start(input);

        assertEquals(15, actual.size());
        assertEquals("Cd 99999999 1200.00 10.00", actual.get(0));
        assertEquals("Savings 77777777 0.00 3.40", actual.get(1));
        assertEquals("withdraw 77777777 30", actual.get(2));
        assertEquals("Checking 88888888 0.00 0.10", actual.get(3));
        assertEquals("withdraw 88888888 0", actual.get(4));
        assertEquals("withdraw 88888888 400", actual.get(5));

        assertEquals("withdraw 99999999 3000", actual.get(6));
        assertEquals("withdraw 77777777 1001", actual.get(7));
        assertEquals("withdraw 77777777 69", actual.get(8));
        assertEquals("withdraw 88888888 401", actual.get(9));
        assertEquals("withdraw 88888888 -1", actual.get(10));
        assertEquals("withdraw 88888888 abc", actual.get(11));
        assertEquals("withdraw 20 2", actual.get(12));
        assertEquals("withdraw 2", actual.get(13));
        assertEquals("withdraw", actual.get(14));

    }

}
