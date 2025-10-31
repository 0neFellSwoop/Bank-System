import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {

    String[] command;
    DepositValidator validator;
    Bank BANK;
    Account CHECKING_ACCOUNT;
    Account CD_ACCOUNT;
    Account SAVINGS_ACCOUNT;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        CHECKING_ACCOUNT = new CheckingAccount(0.5);
        SAVINGS_ACCOUNT = new SavingsAccount(0.5);
        CD_ACCOUNT = new CDAccount(0.5, 1005);
        BANK.addAccount(CHECKING_ACCOUNT);
        BANK.addAccount(SAVINGS_ACCOUNT);
        BANK.addAccount(CD_ACCOUNT);
        validator = new DepositValidator();
    }

    @Test
    void deposit_command_is_case_insensitive(){
        command = new String[]{"dEpOsit", SAVINGS_ACCOUNT.getID(), "500"};
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void cannot_deposit_negatives(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "-450"};
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void can_deposit_zero(){
        command = new String[]{"deposit", SAVINGS_ACCOUNT.getID(), "0"};
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

}
