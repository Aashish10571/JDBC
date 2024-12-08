/*
 :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::               __ ___         __          ._      _________                                           __  ._                         ::
 ::             /   |   \  _____/  |_  ____ |  |    \______   \ ____   ______ ______________  _______ _/  |_|__| ____   ____            ::
 ::            /    ~    \/  _ \   __\/ __ \|  |     |       _// __ \ /  ___// __ \_  __ \  \/ /\__  \\   __\  |/  _ \ /    \           ::
 ::            \    Y    (  <_> )  | \  ___/|  |__   |    |   \  ___/ \___ \\  ___/|  | \/\   /  / __ \|  | |  (  <_> )   |  \          ::
 ::             \___|_  / \____/|__|  \___  >____/   |____|_  /\___  >____  >\___  >__|    \_/  (____  /__| |__|\____/|___|  /          ::
 ::                   \/                  \/                \/     \/     \/     \/                  \/                    \/           ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                              ---------------------------- JDBC PRACTICE PROJECT ----------------------------                        ::
 ::                                                                                                                                     ::
 ::                                                                                                                                     ::
 ::                                                                                                                    - without gui    ::
 ::                                                                                                                                     ::
 :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: :: ::
*/

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "root";
        String password = "W7301@jqir#";

        int menu = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            drawReservation();
            printMenu();

            while (menu != 6) {
                System.out.println();
                System.out.print("Operation code : ");
                menu = input.nextInt();

                switch (menu) {
                    case 1:
                        newReservation(statement, input);
                        break;
                    case 2:
                        checkReservation(statement);
                        break;
                    case 3:
                        getRoomDetails(statement, input);
                        break;
                    case 4:
                        updateReservation(statement, input);
                        break;
                    case 5:
                        cancelReservation(statement, input);
                        break;
                    case 6:
                        exitProgram();
                        break;
                }
            }

            input.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawReservation() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("\t  __ ___          __          ._      _________                                          __   ._");
        System.out.println("\t /   |   \\  _____/  |_  ____ |  |    \\______   \\ ____   ______ ______________  _______ _/  |_|__| ____   ____");
        System.out.println("\t/    ~    \\/  _ \\   __\\/ __ \\|  |     |       _// __ \\ /  ___// __ \\_  __ \\  \\/ /\\__  \\\\   __\\  |/  _ \\ /    \\");
        System.out.println("\t\\    Y    (  <_> )  | \\  ___/|  |__   |    |   \\  ___/ \\___ \\\\  ___/|  | \\/\\   /  / __ \\|  | |  (  <_> )   |  \\");
        System.out.println("\t \\___|_  / \\____/|__|  \\___  >____/   |____|_  /\\___  >____  >\\___  >__|    \\_/  (____  /__| |__|\\____/|___|  /");
        System.out.println("\t       \\/                  \\/                \\/     \\/     \\/     \\/                  \\/                    \\/");
        System.out.println();
        System.out.println();
        System.out.println("\t                ---------------------------- JDBC PRACTICE PROJECT ----------------------------");
        System.out.println();
        System.out.println();
    }

    public static void printMenu() {
        System.out.println();
        System.out.println(" Your Oasis of Comfort and Hospitality");
        System.out.println("   1 -> New reservation");
        System.out.println("   2 -> Check reservation");
        System.out.println("   3 -> Get room number");
        System.out.println("   4 -> Update reservation");
        System.out.println("   5 -> Cancel reservation");
        System.out.println("   6 -> Exit from program\n");
        System.out.println(" Choose numbers on the left to perform operations on the right\n");
    }

    public static void newReservation(Statement statement, Scanner input) {
        System.out.println();

        System.out.println("Please fill the information below for Booking a room");
        System.out.println();

        System.out.print("Guest Name : ");
        String name = input.next();

        System.out.print("Room Number : ");
        int room_no = input.nextInt();

        System.out.print("Contact Number : ");
        String phone_no = input.next();
        while (phone_no.length() > 10) {
            System.out.println("Contact Number cannot have more than 10 digits \n");
            System.out.print("Enter again : ");
            phone_no = input.next();
        }

        String query = "insert into reservation (guest_name, room_number, contact_number) values ('" + name + "', " + room_no + ", '" + phone_no + "')";

        try {
            int rowsAffected = statement.executeUpdate(query);

            System.out.println();
            if (rowsAffected > 0) {
                System.out.println("Your Reservation was successful, see you there");
            } else {
                System.out.println("Error occurred, Try placing your reservation again");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkReservation(Statement statement) {
        System.out.println();

        try {
            ResultSet data = statement.executeQuery("select reservation_id, guest_name, room_number, contact_number, reservation_date from reservation");

            if (data.next()) {
                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
                System.out.println("| Reservation ID | Guest Name      | Room Number   | Contact Number       | Reservation Date        |");
                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

                printDetails(data);
                while (data.next()) {
                    printDetails(data);
                }

                System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            } else {
                System.out.println("No Reservations has been made yet");
            }

            data.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printDetails(ResultSet data) {
        try {
            int id = data.getInt("reservation_id");
            String name = data.getString("guest_name");
            int room = data.getInt("room_number");
            String phone_no = data.getString("contact_number");
            String date = data.getTimestamp("reservation_date").toString();

            System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n", id, name, room, phone_no, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getRoomDetails(Statement statement, Scanner input) {
        System.out.println();

        try {
            System.out.println("Please fill the following information to see the reserved room");
            System.out.println();

            System.out.print("Reservation Id : ");
            int id = input.nextInt();

            System.out.print("Guest Name : ");
            String name = input.next();

            String query = "select room_number from reservation where reservation_id = " + id + " and guest_name = '" + name + "'";
            ResultSet data = statement.executeQuery(query);

            System.out.println();
            if (data.next()) {
                int room = data.getInt("room_number");
                System.out.println("Room number for Reservation Id " + id + " and Guest " + name + " is " + room);
            } else {
                System.out.println("Reservation was not found for the given id and name.");
            }

            data.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateReservation(Statement statement, Scanner input) {
        System.out.println();

        LinkedList<Integer> ID = new LinkedList<>();

        try {
            ResultSet data = statement.executeQuery("select reservation_id from reservation");

            if (data.next()) {
                System.out.println("Please fill the following information to make changes on your reservations\n");

                while (data.next()) {
                    ID.add(data.getInt("reservation_id"));
                }

                System.out.print("Reservation Id : ");
                int id = input.nextInt();

                System.out.println();
                if (ID.contains(id)) {
                    System.out.println("Enter the new data if you want to make changes, enter the same data if you don't want changes\n");

                    System.out.print("New Guest Name : ");
                    String name = input.next();

                    System.out.print("New Room Number : ");
                    int room = input.nextInt();

                    System.out.print("New Contact Number : ");
                    String phone_no = input.next();
                    while (phone_no.length() > 10) {
                        System.out.println("Contact Number cannot have more than 10 digits \n");
                        System.out.print("Enter again : ");
                        phone_no = input.next();
                    }

                    String query = "update reservation set guest_name = '" + name + "', room_number = " + room + ", contact_number = " + phone_no + " where reservation_id = " + id;

                    int rowAffected = statement.executeUpdate(query);

                    System.out.println();
                    if (rowAffected > 0) {
                        System.out.println("Update Successful, Contact us for further changes");
                    } else {
                        System.out.println("Error occurred while updating your data, Try again");
                    }
                } else {
                    System.out.println("Invalid Id, Please Enter a valid Id");
                }
            } else {
                System.out.println("No Reservations has been made yet");
            }

            ID.clear();
            data.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cancelReservation(Statement statement, Scanner input) {
        System.out.println();

        LinkedList<Integer> ID = new LinkedList<>();
        LinkedList<String> NAME = new LinkedList<>();


        try {
            ResultSet data = statement.executeQuery("select reservation_id, guest_name from reservation");

            if (data.next()) {
                System.out.println("Please fill the following information to cancel your reservation\n");

                while (data.next()) {
                    ID.add(data.getInt("reservation_id"));
                    NAME.add(data.getString("guest_name"));
                }

                System.out.print("Reservation Id : ");
                int id = input.nextInt();

                System.out.print("Guest Name : ");
                String name = input.next();

                if (ID.contains(id) && NAME.contains(name)) {
                    String query = "delete from reservation where reservation_id = " + id + " and guest_name = '" + name + "'";

                    int rowAffected = statement.executeUpdate(query);

                    System.out.println();
                    if (rowAffected > 0) {
                        System.out.println("Your reservation has been successfully canceled, Make another reservation?");
                    } else {
                        System.out.println("Error occurred while canceling your reservation, Try canceling again");
                    }
                } else {
                    System.out.println("Could not find reservation for the given id and name");
                }
            } else {
                System.out.println("No Reservations has been made yet");
            }

            ID.clear();
            NAME.clear();
            data.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exitProgram() {
        int i = 1;
        System.out.println("\n\n\t\t\t\t\t\t\tVisit Us Again: Where Memories and Hospitality Awaits You!\n");
        System.out.print("\t\t\t\t\t\t\t\t\t\t\tClosing Application.");

        while (i < 5) {
            i++;

            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println();
    }
}