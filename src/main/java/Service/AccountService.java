package Service;

import java.sql.SQLException;

import DAO.AccountDao;
import Model.Account;

public class AccountService {

    private AccountDao accountDao = new AccountDao();
    
    public Account registerAccount(Account newAccount) throws SQLException {

        Account selectAccount = new Account();
        
        if (accountDao.insertNewAccount(newAccount)) {
            selectAccount = accountDao.selectAccount(newAccount.getUsername(),newAccount.getPassword());
        }

        return selectAccount;
    }

    public Account loginAccount(Account newAccount) throws SQLException {
        return accountDao.selectAccount(newAccount.getUsername(),newAccount.getPassword());
    }
}
