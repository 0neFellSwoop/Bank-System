import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    }


}
