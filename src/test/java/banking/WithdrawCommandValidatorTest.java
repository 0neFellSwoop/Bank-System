package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidatorTest {

    Account checkingAccount;
    Account savingsAccount;
    Account CDAccount;
    Bank bank;
    WithdrawCommandValidator validator;
    CommandProcessor processor;
    String[] command;

    @BeforeEach
    void setUp(){
        checkingAccount = new CheckingAccount("12345678", 3.5);
        savingsAccount = new SavingsAccount("12345677", 4);
        CDAccount = new CDAccount("12345666", 10, 1000);
        bank = new Bank();
        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(CDAccount);
        savingsAccount.deposit(1000);
        processor = new CommandProcessor(bank);
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

    @Test
    void invalid_ID_number_in_withdraw(){
        command = "withdraw 12345688 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_zero(){
        command = "withdraw 12345678 0".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_withdraw_negative(){
        command = "withdraw 12345678 -50".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_to_withdraw_more_than_400_from_checking_account(){
        command = "withdraw 12345678 500".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_withdraw_max_from_checking_account(){
        command = "withdraw 12345678 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_withdraw_multiple_times_from_checking_account(){
        command = "withdraw 12345678 200".split(" ");
        boolean actual = validator.validate(command, bank);
        actual = actual && validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_withdraw_from_savings(){
        command = "withdraw 12345677 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_withdraw_negative_from_savings(){
        command = "withdraw 12345677 -400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_withdraw_zero_from_savings(){
        command = "withdraw 12345677 0".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_withdraw_max_from_savings(){
        command = "withdraw 12345677 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_withdraw_more_than_1000_from_savings(){
        command = "withdraw 12345677 4000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_to_withdraw_from_savings_several_times_without_time_between(){
        command = "withdraw 12345677 0".split(" ");
        boolean actual = validator.validate(command, bank);
        processor.process("withdraw 12345677 0");
        actual = actual && validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_withdraw_from_savings_with_months_between(){
        command = "withdraw 12345677 0".split(" ");
        boolean actual = validator.validate(command, bank);
        processor.process("withdraw 12345677 0");
        processor.process("pass 1");
        actual = actual && validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_withdraw_from_new_CD(){
        command = "withdraw 12345666 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_to_withdraw_from_CD_half_matured(){
        command = "withdraw 12345666 1000".split(" ");
        processor.process("pass 6");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_partial_withdrawal_from_CD(){
        command = "withdraw 12345666 500".split(" ");
        processor.process("pass 12");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_CD_withdrawal_at_12_months(){
        command = "withdraw 12345666 2000".split(" ");
        processor.process("pass 12");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_CD_withdrawal_far_after_maturity(){
        command = "withdraw 12345666 20000".split(" ");
        processor.process("pass 24");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

}
