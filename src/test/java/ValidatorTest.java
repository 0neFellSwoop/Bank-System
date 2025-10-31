import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidatorTest {

    String command;
    Validator validator;

    @BeforeEach
    void setUp(){
        validator = new Validator();

    }

    @Test
    void invalid_command_type(){
        command = "blah";
        boolean actual = validator.validate(command);
        assertFalse(actual);
    }


}
