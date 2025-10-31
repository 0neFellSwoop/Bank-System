import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {

    String[] command;
    CreateValidator validator = new CreateValidator();

    @Test
    void missing_account_type_in_command(){
        command = "create".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void missing_ID_in_command(){
        command = "create checking".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void missing_APR_in_command(){
        command = "create checking 12345678".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void invalid_account_type(){
        command = "create secret 12345678 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void ID_too_short(){
        command = "create checking 1234567 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void ID_too_long(){
        command = "create checking 123456789 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void ID_not_a_number(){
        command = "create checking abcdefgh 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void largest_possible_ID_number(){
        command = "create checking 99999999 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void smallest_possible_ID_number(){
        command = "create checking 00000000 0.4".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void no_negative_APR(){
        command = "create checking 12345678 -0.4".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void too_large_APR(){
        command = "create checking 12345678 12".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void APR_not_a_number(){
        command = "create checking 12345678 a".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void upper_bound_APR(){
        command = "create checking 12345678 10".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void lower_bound_APR(){
        command = "create checking 12345678 0".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void CD_account_missing_inital_deposit(){
        command = "create CD 12345678 6.9".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void no_negative_CD_account_inital_amounts(){
        command = "create CD 12345678 6.9 -21".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void too_small_CD_account_inital_amount(){
        command = "create CD 12345678 6.9 500".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void too_large_CD_account_inital_amount(){
        command = "create CD 12345678 6.9 10004".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void CD_account_inital_amount_not_a_number(){
        command = "create CD 12345678 6.9 xyz".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

}
