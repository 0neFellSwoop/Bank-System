package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    Bank bank;
    CommandProcessor commandProcessor;
    String command;
    Account checkingAccount;
    Account savingsAccount;
    Account CDAccount;

    @BeforeEach
    void setUp(){
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
        checkingAccount = new CheckingAccount("12345678", 3.6);
        savingsAccount = new SavingsAccount("12345677", 5);
        CDAccount = new CDAccount("12345666", 10, 1000);
        bank.addAccount(checkingAccount);
        bank.addAccount(savingsAccount);
        bank.addAccount(CDAccount);
    }

    @Test
    void create_checking_account(){
        command = "create checking 11111111 0.1";
        commandProcessor.process(command);
        assertEquals("11111111", bank.retrieveAccount("11111111").getID());
        assertEquals(0.1, bank.retrieveAccount("11111111").getAPR());
    }

    @Test
    void create_savings_account(){
        command = "create savings 11111111 4.1";
        commandProcessor.process(command);
        assertEquals("11111111", bank.retrieveAccount("11111111").getID());
        assertEquals(4.1, bank.retrieveAccount("11111111").getAPR());
    }

    @Test
    void create_cd_account(){
        command = "create cd 11111111 9.7 2000";
        commandProcessor.process(command);
        assertEquals("11111111", bank.retrieveAccount("11111111").getID());
        assertEquals(9.7, bank.retrieveAccount("11111111").getAPR());
        assertEquals(2000, bank.retrieveAccount("11111111").getBalance());
    }

    @Test
    void deposit_into_checking_account(){
        command = "deposit 12345678 500";
        commandProcessor.process(command);
        assertEquals(500, checkingAccount.getBalance());
    }

    @Test
    void deposit_into_savings_account(){
        command = "deposit 12345677 1500";
        commandProcessor.process(command);
        assertEquals(1500, savingsAccount.getBalance());
    }

    @Test
    void deposit_several_times_works_as_expected(){
        command = "deposit 12345678 1500";
        commandProcessor.process(command);
        commandProcessor.process(command);
        assertEquals(3000, checkingAccount.getBalance());
    }

    @Test
    void pass_deleted_empty_accounts(){
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    void pass_does_not_delete_accounts_with_money(){
        savingsAccount.deposit(30);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(2, bank.getAccounts().size());
    }

    @Test
    void lowest_balance_to_avoid_minimum_balance_fee_from_pass_time(){
        savingsAccount.deposit(100);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(100+100*(0.05/12), savingsAccount.getBalance());
    }

    @Test
    void minimum_balance_fee(){
        checkingAccount.deposit(99);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(74+74*(0.036/12), checkingAccount.getBalance());
    }

    @Test
    void minimum_balance_fee_cannot_reduce_account_to_negative(){
        checkingAccount.deposit(10);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(0, checkingAccount.getBalance());
    }

    @Test
    void minimum_balance_fee_minimum_value_without_account_deletion(){
        checkingAccount.deposit(0.01);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(0, checkingAccount.getBalance());
    }

    @Test
    void pass_time_account_deletion_multiple_times_works_as_intended(){
        checkingAccount.deposit(10);
        command = "pass 1";
        commandProcessor.process(command);
        commandProcessor.process(command);
        assertEquals(1, bank.getAccounts().size());
    }

    @Test
    void pass_time_accrues_interest_with_checking_accounts(){
        checkingAccount.deposit(200);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(200+200*0.036/12, checkingAccount.getBalance());
    }

    @Test
    void pass_time_accrues_interest_with_savings_accounts(){
        savingsAccount.deposit(200);
        command = "pass 1";
        commandProcessor.process(command);
        assertEquals(200+200*0.05/12, savingsAccount.getBalance());
    }

    @Test
    void pass_time_accrues_interest_with_CD_accounts(){
        double actualBalance = CDAccount.getBalance();
        command = "pass 1";
        commandProcessor.process(command);
        for(int i = 0; i < 4; i++){
            actualBalance += actualBalance*0.10/12;
        }
        assertEquals(actualBalance, CDAccount.getBalance());
    }

    @Test
    void pass_time_accrues_interest_over_long_time_periods_on_checking_accounts(){
        double actualBalance = 100;
        checkingAccount.deposit(actualBalance);
        command = "pass 12";
        commandProcessor.process(command);
        for(int i = 0; i < 12; i++){
            actualBalance += actualBalance*0.036/12;
        }
        assertEquals(actualBalance, checkingAccount.getBalance());
    }

    @Test
    void pass_time_accrues_interest_over_long_time_periods_on_savings_accounts(){
        double actualBalance = 100;
        savingsAccount.deposit(actualBalance);
        command = "pass 12";
        commandProcessor.process(command);
        for(int i = 0; i < 12; i++){
            actualBalance += actualBalance*0.05/12;
        }
        assertEquals(actualBalance, savingsAccount.getBalance());
    }

    @Test
    void pass_time_accrues_interest_over_long_time_periods_on_CD_accounts(){
        double actualBalance = CDAccount.getBalance();
        command = "pass 12";
        commandProcessor.process(command);
        for(int i = 0; i < 48; i++){
            actualBalance += actualBalance*0.10/12;
        }
        assertEquals(actualBalance, CDAccount.getBalance());
    }

    @Test
    void withdraw_from_checking_reduces_balance_appropriately(){
        checkingAccount.deposit(50);
        command = "withdraw 12345678 30";
        commandProcessor.process(command);
        assertEquals(50-30, checkingAccount.getBalance());
    }

    @Test
    void withdraw_from_checking_works_multiple_times(){
        checkingAccount.deposit(50);
        command = "withdraw 12345678 10";
        commandProcessor.process(command);
        commandProcessor.process(command);
        assertEquals(50-20, checkingAccount.getBalance());
    }

    @Test
    void withdraw_from_savings_reduces_balance_appropriately(){
        savingsAccount.deposit(200);
        command = "withdraw 12345677 50";
        commandProcessor.process(command);
        assertEquals(200-50, savingsAccount.getBalance());
    }

    @Test
    void withdraw_from_savings_multiple_times_with_months_between(){
        savingsAccount.deposit(500);
        command = "withdraw 12345677 100";
        commandProcessor.process(command);
        commandProcessor.process("pass 1");
        commandProcessor.process(command);
        assertEquals(400+400*0.05/12-100, savingsAccount.getBalance());
    }

    @Test
    void withdraw_zero_works_as_expected(){
        command = "withdraw 12345677 0";
        commandProcessor.process(command);
        assertEquals(0, savingsAccount.getBalance());
    }

    @Test
    void withdraw_from_CD_reduces_balance_appropriately(){
        command = "withdraw 12345666 1000";
        commandProcessor.process(command);
        assertEquals(0, CDAccount.getBalance());
    }

    @Test
    void CD_account_deleted_after_mature_withdraw_and_pass_time(){
        commandProcessor.process("pass 12");
        commandProcessor.process("withdraw 12345666 2000");
        commandProcessor.process("pass 1");
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    void transfer_from_account_removes_money_from_sender(){
        checkingAccount.deposit(200);
        commandProcessor.process("transfer 12345678 12345677 100");
        assertEquals(100, checkingAccount.getBalance());
    }

    @Test
    void transfer_from_account_adds_money_to_destination(){
        checkingAccount.deposit(200);
        commandProcessor.process("transfer 12345678 12345677 100");
        assertEquals(100, savingsAccount.getBalance());
    }

    @Test
    void transfer_overdrafts_result_in_zero_balance(){
        checkingAccount.deposit(200);
        commandProcessor.process("transfer 12345678 12345677 250");
        assertEquals(0, checkingAccount.getBalance());
    }

    @Test
    void transfer_overdrafts_result_in_correct_destination_balance(){
        checkingAccount.deposit(200);
        commandProcessor.process("transfer 12345678 12345677 250");
        assertEquals(200, savingsAccount.getBalance());
    }

    @Test
    void transfer_account_balance_works_as_intended(){
        checkingAccount.deposit(300);
        commandProcessor.process("transfer 12345678 12345677 300");
        assertEquals(300, savingsAccount.getBalance());
        assertEquals(0, checkingAccount.getBalance());
    }


}
