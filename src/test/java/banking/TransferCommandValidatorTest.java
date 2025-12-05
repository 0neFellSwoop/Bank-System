package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandValidatorTest {

    TransferCommandValidator validator;
    String[] command;
    Bank bank;
    Account checkingAccount;
    Account otherCheckingAccount;
    Account savingsAccount;
    Account otherSavingsAccount;
    Account CDAcccount;

    @BeforeEach
    void setUp(){
        bank = new Bank();
        checkingAccount = new CheckingAccount("11111111", 3.6);
        otherCheckingAccount = new CheckingAccount("22222222", 3.6);
        savingsAccount = new SavingsAccount("33333333", 5);
        otherSavingsAccount = new SavingsAccount("44444444", 5);
        CDAcccount = new CDAccount("99999999", 10, 2500);
        checkingAccount.deposit(200);
        savingsAccount.deposit(500);
        bank.addAccount(checkingAccount);
        bank.addAccount(otherCheckingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(otherSavingsAccount);
        bank.addAccount(CDAcccount);
        validator = new TransferCommandValidator();
    }

    @Test
    void invalid_transfer_with_no_arguments(){
        command = "transfer".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_transfer_missing_target_ID(){
        command = "transfer 11111111 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_transfer_missing_amount(){
        command = "transfer 11111111 22222222".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_transfer_too_many_arguments(){
        command = "transfer 11111111 22222222 100 4".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_sender_ID(){
        command = "transfer 12312312 22222222 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_destination_ID(){
        command = "transfer 22222222 12312312 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_if_transfer_amount_is_not_a_double(){
        command = "transfer 22222222 11111111 10AM".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_if_transfer_sender_is_CD(){
        command = "transfer 99999999 11111111 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_if_transfer_destination_is_CD(){
        command = "transfer 11111111 99999999 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_transfer_between_checking_accounts(){
        command = "transfer 11111111 22222222 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_between_savings_accounts(){
        command = "transfer 33333333 44444444 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_from_checking_to_savings(){
        command = "transfer 11111111 33333333 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_from_savings_to_checking(){
        command = "transfer 33333333 22222222 100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_max_withdrawal_from_savings(){
        command = "transfer 33333333 44444444 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_transfer_too_much_from_savings(){
        command = "transfer 33333333 44444444 1001".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }
    @Test
    void invalid_to_transfer_negative_from_savings(){
        command = "transfer 33333333 44444444 -100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_transfer_zero_from_savings(){
        command = "transfer 33333333 44444444 0".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_max_withdrawal_from_checking(){
        command = "transfer 11111111 44444444 400".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_transfer_too_much_from_checking(){
        command = "transfer 11111111 44444444 401".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }
    @Test
    void invalid_to_transfer_negative_from_checking(){
        command = "transfer 11111111 44444444 -100".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_transfer_zero_from_checking(){
        command = "transfer 11111111 44444444 0".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void valid_to_transfer_max_deposit_into_checking(){
        command = "transfer 33333333 11111111 1000".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

    @Test
    void invalid_to_transfer_too_much_into_checking(){
        command = "transfer 33333333 11111111 1001".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void invalid_to_transfer_too_much_into_savings(){
        command = "transfer 33333333 44444444 2501".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }

    @Test
    void valid_to_transfer_total_balance(){
        command = "transfer 11111111 22222222 200".split(" ");
        boolean actual = validator.validate(command, bank);
        assertTrue(actual);
    }

}
