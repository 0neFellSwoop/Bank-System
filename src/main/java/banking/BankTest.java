package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class BankTest {

    Bank bank;
    double APR = 9.9;
    Account account = new CheckingAccount("12345678", APR);
    String ID = account.getID();
    Account account2 = new SavingsAccount("12345679", APR);
    double AMOUNT = 55.43;
    double balance;

    @BeforeEach
    public void setUp(){
        bank = new Bank();
        account.deposit(99.50);
        balance = account.getBalance();
        bank.addAccount(account);
    }

    @Test
    public void A_bank_is_created_with_no_accounts(){
        Assertions.assertTrue(new Bank().getAccounts().isEmpty());
    }

    @Test
    public void when_an_account_is_added_to_the_bank_it_has_one_account_in_it(){
        Assertions.assertEquals(1, bank.getAccounts().size());
    }

    @Test
    public void when_2_accounts_are_added_to_a_bank_the_bank_holds_two_accounts(){
        bank.addAccount(account2);
        Assertions.assertEquals(2, bank.getAccounts().size());
    }

    @Test
    public void retrieve_an_account_from_the_bank(){
        Assertions.assertEquals(account, bank.retrieveAccount(ID));
    }

    @Test
    public void when_retrieving_one_account_from_the_bank_the_correct_account_is_retrieved(){
        bank.addAccount(account2);
        Assertions.assertEquals(ID, bank.retrieveAccount(ID).getID());
    }

    @Test
    public void money_deposited_through_the_bank_goes_to_the_correct_account(){
        bank.deposit(ID, AMOUNT);
        Assertions.assertEquals(account.getBalance(), bank.retrieveAccount(ID).getBalance());
    }

    @Test
    public void money_withdrawn_through_the_bank_is_removed_from_the_right_account(){
        bank.withdraw(ID, AMOUNT);
        Assertions.assertEquals(balance - AMOUNT, bank.retrieveAccount(ID).getBalance());
    }

    @Test
    public void depositing_twice_through_the_bank_works_as_expected(){
        bank.deposit(ID, AMOUNT);
        bank.deposit(ID, AMOUNT * 3);
        Assertions.assertEquals(balance + AMOUNT * 4, bank.retrieveAccount(ID).getBalance());
    }

    @Test
    public void withdrawing_twice_through_the_bank_works_as_expected(){
        bank.withdraw(ID, AMOUNT * 0.2);
        bank.withdraw(ID, AMOUNT * 0.8);
        Assertions.assertEquals(balance - AMOUNT, bank.retrieveAccount(ID).getBalance());
    }


}
