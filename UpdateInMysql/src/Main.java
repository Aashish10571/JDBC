import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Scanner inp = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/aashish";
        String user = "root";
        String password = "W7301@jqir#";

        int menu = 0;

        LinkedList<Integer> DB_id = new LinkedList<>();
        LinkedList<String> DB_name = new LinkedList<>();
        LinkedList<Integer> DB_salary = new LinkedList<>();

        int id, newId, salary, newSalary;
        String name, newName;

        String selectQuery = "select * from employee";
        String updateQuery;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            System.out.println("Select one of the menu from the following");
            System.out.println("1 -> Change Id");
            System.out.println("2 -> Change Name");
            System.out.println("3 -> Change Salary");
            System.out.println("4 -> Quit");
            while (menu != 4) {
                ResultSet resultSet = statement.executeQuery(selectQuery);
                while (resultSet.next()) {
                    DB_id.add(resultSet.getInt("id"));
                    DB_name.add(resultSet.getString("name"));
                    DB_salary.add(resultSet.getInt("salary"));
                }

                System.out.println();
                System.out.print("Menu : ");
                menu = inp.nextInt();

                switch (menu) {
                    case 1:
                        System.out.println();
                        System.out.print("Id : ");
                        id = inp.nextInt();

                        if (DB_id.contains(id)) {
                            System.out.print("New Id : ");
                            newId = inp.nextInt();

                            updateQuery = "update employee set id = " + newId + " where id = " + id;
                            int rowsAffected = statement.executeUpdate(updateQuery);

                            System.out.println();
                            if (rowsAffected > 0) {
                                System.out.println("Update Successful, " + rowsAffected + " rows affected.");
                            } else {
                                System.out.println("Failed to Update!!!");
                            }
                        } else {
                            System.out.println("Invalid Id!!!");
                        }
                        break;
                    case 2:
                        System.out.println();
                        System.out.print("Name : ");
                        name = inp.next();

                        if (DB_name.contains(name)) {
                            System.out.print("New Name : ");
                            newName = inp.next();

                            updateQuery = "update employee set name = '" + newName + "' where name = '" + name + "'";
                            int rowsAffected = statement.executeUpdate(updateQuery);

                            System.out.println();
                            if (rowsAffected > 0) {
                                System.out.println("Update Successful, " + rowsAffected + " rows affected.");
                            } else {
                                System.out.println("Failed to Update!!!");
                            }
                        } else {
                            System.out.println("Invalid Name!!!");
                        }
                        break;
                    case 3:
                        System.out.println();
                        System.out.print("Salary : ");
                        salary = inp.nextInt();

                        if (DB_salary.contains(salary)) {
                            System.out.print("New Salary : ");
                            newSalary = inp.nextInt();

                            updateQuery = "update employee set salary = " + newSalary + " where salary = " + salary;
                            int rowsAffected = statement.executeUpdate(updateQuery);

                            System.out.println();
                            if (rowsAffected > 0) {
                                System.out.println("Update Successful, " + rowsAffected + " rows affected.");
                            } else {
                                System.out.println("Failed to Update!!!");
                            }
                        } else {
                            System.out.println("Invalid Salary!!!");
                        }
                        break;
                }
                DB_id.clear();
                DB_name.clear();
                DB_salary.clear();
                resultSet.close();
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}