package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    Bank bank;
    CommandProcessor commandProcessor;
    String command;
    Account account;

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

    @Test
    void create_savings_account(){
        command = "create savings 12345678 4.1";
        commandProcessor.process(command);
        assertEquals("12345678", bank.retrieveAccount("12345678").getID());
        assertEquals(4.1, bank.retrieveAccount("12345678").getAPR());
    }

    @Test
    void create_cd_account(){
        command = "create cd 12345678 9.7 2000";
        commandProcessor.process(command);
        assertEquals("12345678", bank.retrieveAccount("12345678").getID());
        assertEquals(9.7, bank.retrieveAccount("12345678").getAPR());
        assertEquals(2000, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void deposit_into_checking_account(){
        account = new CheckingAccount("12345678", .6);
        bank.addAccount(account);
        command = "deposit 12345678 500";
        commandProcessor.process(command);
        assertEquals(500, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void deposit_into_savings_account(){
        account = new SavingsAccount("12345678", 5);
        bank.addAccount(account);
        command = "deposit 12345678 1500";
        commandProcessor.process(command);
        assertEquals(1500, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void deposit_several_times_works_as_expected(){
        account = new SavingsAccount("12345678", 5);
        bank.addAccount(account);
        command = "deposit 12345678 1500";
        commandProcessor.process(command);
        commandProcessor.process(command);
        assertEquals(3000, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void pass_deleted_empty_accounts(){
        account = new SavingsAccount("12345678", 5);
        bank.addAccount(account);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    void pass_does_not_delete_accounts_with_money(){
        account = new CDAccount("12345678", 5, 1500);
        bank.addAccount(account);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    void lowest_balance_to_avoid_minimum_balance_fee_from_pass_time(){
        account = new CheckingAccount("12345678", 5);
        bank.addAccount(account);
        account.deposit(100);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(100, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void minimum_balance_fee(){
        account = new CheckingAccount("12345678", 5);
        bank.addAccount(account);
        account.deposit(99);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(74, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void minimum_balance_fee_cannot_reduce_account_to_negative(){
        account = new CheckingAccount("12345678", 5);
        bank.addAccount(account);
        account.deposit(10);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(0, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void minimum_balance_fee_minimum_value_without_account_deletion(){
        account = new CheckingAccount("12345678", 5);
        bank.addAccount(account);
        account.deposit(0.01);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(0, bank.retrieveAccount("12345678").getBalance());
    }

    @Test
    void pass_time_account_deletion_multiple_times_works_as_intended(){
        account = new SavingsAccount("12345678", 5);
        Account account2 = new CheckingAccount("12345677", 3);
        bank.addAccount(account);
        bank.addAccount(account2);
        account.deposit(0.01);
        command = "pass 1";
        commandProcessor.process(command);
        commandProcessor.process(command);
        assertEquals(0, bank.getAccounts().size());
    }



}
