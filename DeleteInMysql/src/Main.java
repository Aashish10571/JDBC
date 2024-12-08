import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Scanner inp = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/aashish";
        String userName = "root";
        String password = "W7301@jqir#";

        boolean valid;
        char menu = 'y';

        LinkedList<String> DB_name = new LinkedList<>();

        String name;

        String selectQuery = "select * from employee";
        String deleteQuery;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                DB_name.add(resultSet.getString("name"));
            }

            System.out.println("Press any key to continue, n to quit.");
            System.out.println();
            System.out.println("Enter the requested data to delete from the database.");

            while (menu != 'n') {
                System.out.println();
                System.out.print("name : ");
                name = inp.next();

                if (DB_name.contains(name)) {
                    DB_name.remove(name);
                    valid = true;
                } else {
                    valid = false;
                }

                if (valid) {
                    deleteQuery = "delete from employee where name = '" + name + "'";

                    int rowAffected = statement.executeUpdate(deleteQuery);

                    System.out.println();
                    if (rowAffected > 0) {
                        System.out.println("Deletion Successful, " + rowAffected + " rows affected.");
                    } else {
                        System.out.println("Deletion Invalid!!!");
                    }
                } else {
                    System.out.println();
                    System.out.println("Invalid data, try again!!!");
                }

                System.out.println();
                System.out.print("Enter again? : ");
                menu = inp.next().charAt(0);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}