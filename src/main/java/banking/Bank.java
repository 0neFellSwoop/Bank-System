package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<String, Account> accounts;
    private int numberOfAccounts;

    Bank() {
        accounts = new HashMap<>();
        numberOfAccounts = 0;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        numberOfAccounts++;
        account.setAccountNumber(numberOfAccounts);
        accounts.put(account.getID(), account);

    }

    public Account retrieveAccount(String id){
        return accounts.getOrDefault(id, null);
    }

    public void deposit(String id, double depositAmount) {
        retrieveAccount(id).deposit(depositAmount);
    }

    public void withdraw(String id, double withdrawAmount) {
        retrieveAccount(id).withdraw(withdrawAmount);
    }

    public boolean validateTransfer(String senderID, String destinationID, double amount) {
        return this.retrieveAccount(senderID).validateWithdrawal(amount) && this.retrieveAccount(senderID).validateDeposit(amount) && this.retrieveAccount(destinationID).validateDeposit(amount);
    }
}
