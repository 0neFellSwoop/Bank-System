package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DepositCommandValidatorTest {

    String[] command;
    DepositCommandValidator validator;
    Bank BANK;
    Account CHECKING_ACCOUNT;
    Account CD_ACCOUNT;
    Account SAVINGS_ACCOUNT;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        CHECKING_ACCOUNT = new CheckingAccount("11345678", 0.5);
        SAVINGS_ACCOUNT = new SavingsAccount("11145678", 0.5);
        CD_ACCOUNT = new CDAccount("11115678", 0.5, 1005);
        BANK.addAccount(CHECKING_ACCOUNT);
        BANK.addAccount(SAVINGS_ACCOUNT);
        BANK.addAccount(CD_ACCOUNT);
        validator = new DepositCommandValidator();
    }

    @Test
    void deposit_command_is_case_insensitive(){
        command = new String[]{"dEpOsit", SAVINGS_ACCOUNT.getID(), "500"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

    @Test
    void cannot_deposit_negatives(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "-450"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void can_deposit_zero(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "0"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

    @Test
    void cannot_deposit_letters(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "adasd"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void cannot_deposit_with_extra_arguments(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "500", "123"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void cannot_deposit_with_missing_ID(){
        command = new String[]{"deposit", "500"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void cannot_deposit_with_missing_amount(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID()};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void cannot_deposit_into_CD(){
        command = new String[]{"deposit", CD_ACCOUNT.getID(), "500"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void can_deposit_to_savings_account(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "500"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

    @Test
    void can_deposit_to_checking(){
        command = new String[]{"deposit", CHECKING_ACCOUNT.getID(), "300"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

    @Test
    void cannot_deposit_too_much_into_savings_account(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "2501"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void cannot_deposit_too_much_into_checking_account(){
        command = new String[]{"deposit", CHECKING_ACCOUNT.getID(), "1001"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertFalse(actual);
    }

    @Test
    void max_deposit_into_savings_account(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "2500"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

    @Test
    void max_deposit_into_checking_account(){
        command = new String[]{"deposit", CHECKING_ACCOUNT.getID(), "1000"};
        boolean actual = validator.validate(command, BANK);
        Assertions.assertTrue(actual);
    }

}
