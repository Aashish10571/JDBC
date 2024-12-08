import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/aashish";
        String user = "root";
        String password = "W7301@jqir#";

        String withdrawQuery = "update accounts set balance = balance - ? where account_number = ?";
        String depositQuery = "update accounts set balance = balance + ? where account_number = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);
            PreparedStatement depositStatement = connection.prepareStatement(depositQuery);

            withdrawStatement.setDouble(1, 500.00);
            withdrawStatement.setString(2, "acc456");

            depositStatement.setDouble(1, 500.00);
            depositStatement.setString(2, "acc123");

            int rowsAffectedWithdraw = withdrawStatement.executeUpdate();
            int rowsAffectedDeposit = depositStatement.executeUpdate();

            if (rowsAffectedWithdraw > 0 && rowsAffectedDeposit > 0) {
                connection.commit();
                System.out.println("Transaction Successful.");
            } else {
                connection.rollback();
                System.out.println("Error Occurred, Try again!!!");
            }

            withdrawStatement.close();
            depositStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}