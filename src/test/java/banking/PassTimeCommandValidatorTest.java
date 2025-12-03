package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeCommandValidatorTest {

    String[] command;
    PassTimeCommandValidator validator;

    @BeforeEach
    void setUp(){
        validator = new PassTimeCommandValidator();
    }

    @Test
    void missing_months_to_pass(){
        command = "pass ".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void cannot_pass_zero_months(){
        command = "pass 0".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void time_cannot_be_a_decimal(){
        command = "pass 0.6".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void time_cannot_be_a_string(){
        command = "pass six".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void command_cannot_have_too_many_arguments(){
        command = "pass 6 ifryuhu".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void minimum_month_input(){
        command = "pass 1".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void max_month_input(){
        command = "pass 60".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void normal_month_input(){
        command = "pass 12".split(" ");
        boolean actual = validator.validate(command);
        assertTrue(actual);
    }

    @Test
    void too_large_month_input(){
        command = "pass 61".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }

    @Test
    void cannot_pass_negative_months(){
        command = "pass -11".split(" ");
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }



}
