package Demo;

import java.sql.*;

public class BankService {

    public void registerUser(String name, String email, String password) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.executeUpdate();
        System.out.println("User registered successfully.");
    }

    public void createAccount(String email) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String userQuery = "SELECT user_id FROM users WHERE email = ?";
        PreparedStatement userStmt = conn.prepareStatement(userQuery);
        userStmt.setString(1, email);
        ResultSet rs = userStmt.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("user_id");
            String accQuery = "INSERT INTO accounts (user_id, balance) VALUES (?, 0.0)";
            PreparedStatement accStmt = conn.prepareStatement(accQuery);
            accStmt.setInt(1, userId);
            accStmt.executeUpdate();
            System.out.println("Account created successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void deposit(String email, double amount) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "UPDATE accounts a JOIN users u ON a.user_id = u.user_id SET a.balance = a.balance + ? WHERE u.email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, amount);
        stmt.setString(2, email);
        int rows = stmt.executeUpdate();
        if (rows > 0) System.out.println("Deposit successful.");
        else System.out.println("Deposit failed.");
    }

    public void withdraw(String email, double amount) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String checkQuery = "SELECT a.balance FROM accounts a JOIN users u ON a.user_id = u.user_id WHERE u.email = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, email);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getDouble("balance") >= amount) {
            String updateQuery = "UPDATE accounts a JOIN users u ON a.user_id = u.user_id SET a.balance = a.balance - ? WHERE u.email = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setDouble(1, amount);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void checkBalance(String email) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT a.balance FROM accounts a JOIN users u ON a.user_id = u.user_id WHERE u.email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            System.out.println("Current Balance: ₹" + rs.getDouble("balance"));
        } else {
            System.out.println("Account not found.");
        }
    }
}
