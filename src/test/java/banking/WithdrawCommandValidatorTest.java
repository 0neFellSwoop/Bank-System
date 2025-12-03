package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidatorTest {

    Account account;
    Bank bank;
    WithdrawCommandValidator validator;
    String ID = "12345678";
    String[] command;

    @BeforeEach
    void setUp(){
        account = new CheckingAccount(ID, 3.5);
        bank = new Bank();
        bank.addAccount(account);
        validator = new WithdrawCommandValidator();
    }

    @Test
    void too_few_arguments_in_withdraw_command(){
        command = "withdraw".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void missing_target_ID_for_withdraw_command(){
        command = "withdraw 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void missing_amount_for_withdraw_command(){
        command = "withdraw 12345678".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void too_many_arguments_for_withdraw_command(){
        command = "withdraw 12345678 400 8".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void withdraw_command_has_invalid_ID(){
        command = "withdraw 12345600 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_command(){
        command = "withdraw 12345678 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void withdraw_command_amount_NaN(){
        command = "withdraw 12345678 abc".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }


}
