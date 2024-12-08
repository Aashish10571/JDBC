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

        LinkedList<Integer> DB_id = new LinkedList<>();

        char run = 'y';
        boolean valid;

        int id, salary;
        String name;

        String insertQuery;
        String scanDatabaseQuery = "select * from employee";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(scanDatabaseQuery);

            while (resultSet.next()) {
                DB_id.add(resultSet.getInt("id"));
            }

            System.out.println("Press any key to continue, n to quit.");
            System.out.println();
            System.out.println("Enter the data as requested.");

            while (run != 'n') {
                System.out.println();
                System.out.print("id : ");
                id = inp.nextInt();
                System.out.print("name : ");
                name = inp.next();
                System.out.print("salary : ");
                salary = inp.nextInt();

                if (DB_id.contains(id)) {
                    valid = false;
                } else {
                    DB_id.add(id);
                    valid = true;
                }

                if (valid) {
                    insertQuery = "insert into employee (id, name, salary) values (" + id + ", '" + name + "', " + salary + ")";

                    int rowsAffected = statement.executeUpdate(insertQuery);

                    System.out.println();
                    if (rowsAffected > 0) {
                        System.out.println("Insertion Successful, " + rowsAffected + " rows affected.");
                    } else {
                        System.out.println("Insertion Failed!!!");
                    }

                    System.out.println();
                    System.out.print("Insert again? : ");
                    run = inp.next().charAt(0);
                } else {
                    System.out.println("Invalid data!!!, Enter again.");
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}