package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    Account account;
    double APR = 6.5;
    double balance;
    double AMOUNT = 13.00;

    @BeforeEach
    public void setUp(){
        account = new CheckingAccount("12345678", APR);
        account.deposit(50.00);
        balance = account.getBalance();

    }

    @Test
    void account_is_created_with_the_given_ID() { assertEquals("12345678", account.getID());}

    @Test
    public void account_is_created_with_the_given_APR(){
        assertEquals(APR, account.getAPR());
    }

    /*
    Now handled by banking.CommandValidator

    @Test
    public void each_account_has_unique_ID(){
        banking.Account account2 = new banking.SavingsAccount("12345678", APR);
        banking.Account account3 = new banking.SavingsAccount("12345678", APR);
        assertNotEquals(account.getID(), account2.getID());
        assertNotEquals(account2.getID(), account3.getID());
        assertNotEquals(account.getID(), account3.getID());
    }
    */

    @Test
    public void balance_increases_by_amount_deposited_into_account(){
        account.deposit(AMOUNT);
        assertEquals(balance + AMOUNT, account.getBalance());
    }

    @Test
    public void balance_decreases_by_amount_withdrawn_from_account(){
        account.withdraw(AMOUNT);
        assertEquals(balance - AMOUNT, account.getBalance());
    }

    @Test
    public void balance_cannot_be_negative(){
        account.withdraw(balance + 10.50);
        assertEquals(0, account.getBalance());
    }

    @Test
    public void depositing_twice_in_the_same_account_works_as_expected(){
        account.deposit(AMOUNT);
        account.deposit(AMOUNT * 1.75);
        assertEquals(balance + AMOUNT * 2.75, account.getBalance());
    }

    @Test
    public void withdrawing_from_the_same_account_twice_works_as_expected(){
        account.withdraw(AMOUNT);
        account.withdraw(AMOUNT * 2.0);
        assertEquals(balance - AMOUNT * 3.0, account.getBalance());
    }
}
