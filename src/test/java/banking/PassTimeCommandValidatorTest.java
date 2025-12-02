package banking;

import org.junit.jupiter.api.BeforeEach;

public class PassTimeCommandValidatorTest {

    String[] command;
    PassTimeCommandValidator validator;
    Bank BANK;
    Account account;

    @BeforeEach
    void setUp(){
        BANK = new Bank();
        account = new CheckingAccount("11111111", 0.5);
        BANK.addAccount(account);
        validator = new PassTimeCommandValidator();
    }
}
