import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/aashish";
        String user = "root";
        String password = "W7301@jqir#";

        Scanner input = new Scanner(System.in);

        int choice;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("1 -> Insert Image in Database");
            System.out.println("2 -> Insert Image in Database");

            System.out.print("Enter your choice : ");
            choice = input.nextInt();

            if (choice == 1) {
                insertImage(connection);
            } else if (choice == 2) {
                retrieveImage(connection);
            } else {
                System.out.println("Invalid Input, Enter again");
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertImage(Connection connection) {
        String image_path = "C:\\Users\\Aashish\\Downloads\\apple.png";
        String storeQuery = "insert into imageTable (image) values(?)";

        try {
            FileInputStream fileInputStream = new FileInputStream(image_path);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement = connection.prepareStatement(storeQuery);
            preparedStatement.setBytes(1, imageData);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Insertion successful");
            } else {
                System.out.println("Error occurred");
            }

            preparedStatement.close();
            fileInputStream.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void retrieveImage(Connection connection) {
        String file_path = "C:\\Users\\Aashish\\Downloads\\";

        String retrieveQuery = "select image from imageTable where id = ?";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(retrieveQuery);
            preparedStatement.setInt(1, 1);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image");
                file_path += "apple1.png";
                OutputStream outputStream = new FileOutputStream(file_path);
                outputStream.write(imageData);
            } else {
                System.out.println("Image not found!!!");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}