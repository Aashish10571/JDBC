import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/aashish";
        String user = "root";
        String password = "W7301@jqir#";

        String query = "insert into employee(id, name, salary) values (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Scanner inp = new Scanner(System.in);

            System.out.println("Enter the data as requested below");
            System.out.println();

            while (true) {
                System.out.print("Id : ");
                int id = inp.nextInt();
                System.out.print("Name : ");
                String name = inp.next();
                System.out.print("Salary : ");
                int salary = inp.nextInt();

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, salary);
                preparedStatement.addBatch();

                System.out.print("Enter data again? : ");
                String input = inp.next();
                System.out.println();

                if (input.equalsIgnoreCase("n"))
                    break;
            }

            int[] batchResult = preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Batch executed successfully");

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}