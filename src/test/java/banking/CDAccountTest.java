package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDAccountTest {

    CDAccount CDAccount;
    double APR = 4.5;
    double balance = 1000;

    @BeforeEach
    public void setUp(){
        CDAccount = new CDAccount("12345678", APR, balance);
    }

    @Test
    public void create_CD_account_with_starting_balance(){
        assertEquals(balance, CDAccount.getBalance());
    }
}
