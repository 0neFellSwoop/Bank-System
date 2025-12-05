package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {

    String command;
    CommandValidator commandValidator;
    Bank BANK;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        commandValidator = new CommandValidator(BANK);
    }

    @Test
    void missing_command(){
        boolean actual = commandValidator.validate(null);
        assertFalse(actual);
    }

    @Test
    void invalid_command_type(){
        command = "blah";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_create_command() {
        command = "craete checking 12345678 0.4";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void valid_create_command(){
        command = "create checking 12345678 4";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void valid_create_with_trailing_spaces(){
        command = "create checking 12345678 0.5  ";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void invalid_create_with_extra_spaces_in_the_beginning(){
        command = "   create checking 12345678 0.5";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void invalid_create_with_extra_spaces_in_the_middle(){
        command = "create  checking 12345678  0.5";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_pass_command(){
        command = "passs 12";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void valid_pass_command(){
        command = "pass 4";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void valid_pass_command_with_trailing_spaces(){
        command = "pass 4 ";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void valid_pass_command_with_beginning_spaces(){
        command = " pass 4";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void valid_pass_command_with_middle_spaces(){
        command = "pass  4";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit_command(){
        command = "deepsoit 12345678 454";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void typo_in_withdraw_command(){
        command = "withdraaw 12345678 300";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_command(){
        BANK.addAccount(new CheckingAccount("12345678", 3.6));
        command = "withdraw 12345678 100";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_command_trailing_spaces(){
        BANK.addAccount(new CheckingAccount("12345678", 3.6));
        command = "withdraw 12345678 100   ";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void invalid_withdraw_command_beginning_spaces(){
        BANK.addAccount(new CheckingAccount("12345678", 3.6));
        command = "  withdraw 12345678 100";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_command_middle_spaces(){
        BANK.addAccount(new CheckingAccount("12345678", 3.6));
        command = "withdraw 12345678  100";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void command_type_is_case_insensitive(){
        command = "creaTE checking 12345678 0.4";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void transfer_command_delegation(){
        command = "transfer 12345678 12345678 100";
        boolean actual = commandValidator.validate(command);
        assertFalse(actual);
    }

    @Test
    void valid_transfer_command(){
        BANK.addAccount(new CheckingAccount("12345678", 3.6));
        BANK.addAccount(new CheckingAccount("87654321", 3.6));
        BANK.deposit("12345678", 500);
        command = "transfer 12345678 87654321 300";
        boolean actual = commandValidator.validate(command);
        assertTrue(actual);
    }

    @Test
    void validate_multiple_commands(){
        command = "Create savings 12345678 0.6";
        boolean actual = commandValidator.validate(command);
        CommandProcessor processor = new CommandProcessor(BANK);
        processor.process(command);
        actual = actual && commandValidator.validate("Deposit 12345678 700");
        actual = actual && !commandValidator.validate("Deposit 12345678 5000");
        actual = actual && commandValidator.validate("creAte cHecKing 98765432 0.01");
        processor.process("creAte cHecKing 98765432 0.01");
        actual = actual && commandValidator.validate("Deposit 98765432 300");
        actual = actual && commandValidator.validate("Transfer 98765432 12345678 300");
        actual = actual && commandValidator.validate("Pass 1");
        actual = actual && commandValidator.validate("Create cd 23456789 1.2 2000");
        assertTrue(actual);
    }

}
