package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDao {
    
    private Connection connection = ConnectionUtil.getConnection();

    public Boolean insertNewAccount(Account newAccount) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO account (username, password) VALUES (?,?)");
        ps.setString(1, newAccount.getUsername());
        ps.setString(2, newAccount.getPassword());

        int result = ps.executeUpdate();

        if (result > 0) {
            return true;
        }

        return false;
    }

    public Account selectAccount(String username, String password) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        Account account = new Account();

        while(rs.next()) {
            account.setAccount_id(rs.getInt("account_id"));
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
        }

        return account;
    }
}
