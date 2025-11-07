import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    Bank bank;
    CommandProcessor commandProcessor;
    String command;

    @BeforeEach
    void setUp(){
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void create_checking_account(){
        command = "create checking 12345678 0.1";
        commandProcessor.process(command);
        assertEquals("12345678", bank.retrieveAccount("12345678").getID());
        assertEquals(0.1, bank.retrieveAccount("12345678").getAPR());

    }


}
