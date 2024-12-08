import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/aashish";
        String user = "root";
        String password = "W7301@jqir#";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Scanner input = new Scanner(System.in);

            System.out.println("Menu");
            System.out.println("1 -> Insert");
            System.out.println("2 -> Check");
            System.out.println("3 -> Update");
            System.out.println("4 -> Remove");
            System.out.println("5 -> Exit");
            System.out.println();

            System.out.print("Menu : ");
            int menu = input.nextInt();

            while (menu != 5) {
                switch (menu) {
                    case 1:
                        insertData(connection, input);
                        break;
                    case 2:
                        checkData(connection, input);
                        break;
                    case 3:
                        updateData(connection, input);
                        break;
                    case 4:
                        removeData(connection, input);
                        break;
                }

                System.out.print("Menu : ");
                menu = input.nextInt();
            }

            System.out.println("\nExiting program...");
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkData(Connection connection, Scanner input) {
        String query = "select * from employee where name = ? and id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            System.out.println();

            System.out.println("Enter the details as requested");
            System.out.println();

            System.out.print("Id : ");
            preparedStatement.setInt(2, input.nextInt());

            System.out.print("Name : ");
            preparedStatement.setString(1, input.next());

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println();
            while (resultSet.next()) {
                System.out.println("Id : " + resultSet.getInt("id"));
                System.out.println("Name : " + resultSet.getString("name"));
                System.out.println("Salary : " + resultSet.getInt("salary"));
            }
            System.out.println();

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertData(Connection connection, Scanner input) {
        String query = "insert into employee (id, name, salary) values (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            System.out.println();

            System.out.println("Enter the data as requested");
            System.out.println();

            System.out.print("Id : ");
            preparedStatement.setInt(1, input.nextInt());

            System.out.print("Name : ");
            preparedStatement.setString(2, input.next());

            System.out.print("Salary : ");
            preparedStatement.setInt(3, input.nextInt());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println();
            if (rowsAffected > 0)
                System.out.println("Data was successfully inserted");
            else
                System.out.println("Error occurred while inserting data");

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateData(Connection connection, Scanner input) {
        String query = "update employee set name = ?, salary = ? where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            System.out.println();

            System.out.println("Enter the data you want to change, enter the same if you don't want");
            System.out.println();

            System.out.print("Enter the id you want to make changes on : ");
            preparedStatement.setInt(3, input.nextInt());

            System.out.println();

            System.out.print("New Name : ");
            preparedStatement.setString(1, input.next());

            System.out.print("New Salary : ");
            preparedStatement.setInt(2, input.nextInt());

            System.out.println();

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0) {
                System.out.println("Updated data successfully");
            } else {
                System.out.println("Error occurred while updating data");
            }

            System.out.println();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeData(Connection connection, Scanner input) {
        String query = "delete from employee where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            System.out.println();

            System.out.println("Enter the details of data you want to remove");
            System.out.println();

            System.out.print("Id : ");
            preparedStatement.setInt(1, input.nextInt());

            System.out.println();

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Removed data successfully");
            } else {
                System.out.println("Error occurred while removing");
            }

            System.out.println();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}