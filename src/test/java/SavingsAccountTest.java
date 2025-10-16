import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {

    SavingsAccount savingsAccount;
    double APR = 6.5;

    @BeforeEach
    public void setUp(){
        savingsAccount = new SavingsAccount(APR);
    }

    @Test
    public void create_savings_account_with_zero_balance(){
        assertEquals(0, savingsAccount.getBalance());
    }
}
