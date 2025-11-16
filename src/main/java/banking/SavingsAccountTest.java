package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class SavingsAccountTest {

    SavingsAccount savingsAccount;
    double APR = 6.5;

    @BeforeEach
    public void setUp(){
        savingsAccount = new SavingsAccount("12345678", APR);
    }

    @Test
    public void create_savings_account_with_zero_balance(){
        Assertions.assertEquals(0, savingsAccount.getBalance());
    }
}
