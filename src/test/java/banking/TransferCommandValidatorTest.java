package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        CDAcccount = new CDAccount("99999999", 10, 1000);
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
    void transfer_amount_is_not_a_double(){
        command = "transfer 22222222 11111111 10AM".split(" ");
        boolean actual = validator.validate(command, bank);
        assertFalse(actual);
    }







}
