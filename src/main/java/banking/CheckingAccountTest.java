package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class CheckingAccountTest {

    CheckingAccount checkingAccount;
    double APR = 6.5;

    @BeforeEach
    public void setUp(){
        checkingAccount = new CheckingAccount("12345678", APR);
    }

    @Test
    public void create_checking_account_with_zero_balance(){
        Assertions.assertEquals(0, checkingAccount.getBalance());
    }
}
