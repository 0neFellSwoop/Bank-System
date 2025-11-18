package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {

    String[] command;
    CreateCommandValidator validator;
    Bank BANK;
    Account account;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        account = new CheckingAccount("11111111", 0.5);
        BANK.addAccount(account);
        validator = new CreateCommandValidator();
    }

    @Test
    void missing_account_type_in_command(){
        command = "create".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void missing_ID_in_command(){
        command = "create checking".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void missing_APR_in_command(){
        command = "create checking 12345678".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void invalid_account_type(){
        command = "create secret 12345678 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void ID_too_short(){
        command = "create checking 1234567 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void ID_too_long(){
        command = "create checking 123456789 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void ID_not_a_number(){
        command = "create checking abcdefgh 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void largest_possible_ID_number(){
        command = "create checking 99999999 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void smallest_possible_ID_number(){
        command = "create checking 00000000 0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void no_negative_APR(){
        command = "create checking 12345678 -0.4".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void too_large_APR(){
        command = "create checking 12345678 12".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void APR_not_a_number(){
        command = "create checking 12345678 a".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void upper_bound_APR(){
        command = "create checking 12345678 10".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void lower_bound_APR(){
        command = "create checking 12345678 0".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void CD_account_missing_inital_deposit(){
        command = "create CD 12345678 6.9".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void no_negative_CD_account_inital_amounts(){
        command = "create CD 12345678 6.9 -21".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void too_small_CD_account_inital_amount(){
        command = "create CD 12345678 6.9 500".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void too_large_CD_account_inital_amount(){
        command = "create CD 12345678 6.9 10004".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void CD_account_inital_amount_not_a_number(){
        command = "create CD 12345678 6.9 xyz".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void invalid_CD_creation_with_too_many_arguments(){
        command = "create CD 12345678 6.9 5000 xyz".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void create_CD_account(){
        command = "create CD 12345678 6.9 5000".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void maximum_initial_amount_for_CD_account(){
        command = "create CD 12345678 6.9 10000".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void minimum_initial_amount_for_CD_account(){
        command = "create CD 12345678 6.9 1000".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void create_command_is_case_insensitive(){
        command = "create CheCkINg 12345678 6.9".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void cannot_create_checking_with_intial_amount(){
        command = "create checking 12345678 6.9 5000".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void cannot_create_savings_with_intial_amount(){
        command = "create savings 12345678 6.9 5000".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

    @Test
    void create_savings_account(){
        command = "create savings 12345678 3.6".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void create_checking_account(){
        command = "create checking 12345678 0.5".split(" ");
        boolean actual = validator.validate(command, BANK);
        assertTrue(actual);
    }

    @Test
    void no_duplicate_ID_numbers(){
        command = new String[]{"create", "checking", account.getID(), "0.5"};
        validator.validate(command, BANK);
        boolean actual = validator.validate(command, BANK);
        assertFalse(actual);
    }

}
