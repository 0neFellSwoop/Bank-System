package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<String, Account>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
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
}
