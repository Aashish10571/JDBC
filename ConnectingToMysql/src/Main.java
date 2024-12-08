import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/aashish";
        String userName = "root";
        String password = "W7301@jqir#";

        String query = "select * from employee";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");

                System.out.println(id + "\t" + name + "\t " + salary);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}