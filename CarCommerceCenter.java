import java.sql.*;
import java.util.*;

public class CarCommerceCenter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car";
    private static final String USER = "root";
    private static final String PASSWORD = "0731";

    private static Connection connection;
    private static String currentUserEmail;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            login();
            showMenu();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void login() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Car Commerce Center!");

        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (isValidUser(email, password)) {
                currentUserEmail = email;
                System.out.println("Login successful!\n");
                break;
            } else {
                System.out.println("Invalid email or password. Please try again.\n");
            }
        }
    }

    private static boolean isValidUser(String email, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private static void showMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Add Car");
            System.out.println("2. Delete Car");
            System.out.println("3. Search Cars");
            System.out.println("4. Show All Cars");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    deleteCar();
                    break;
                case 3:
                    searchCars();
                    break;
                case 4:
                    showAllCars();
                    break;
                case 5:
                    System.out.println("धन्यवाद");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    
    private static void searchCars() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();

        String query = "SELECT * FROM Car WHERE Model LIKE ? OR Company LIKE ? OR Colour LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + keyword + "%");
        statement.setString(2, "%" + keyword + "%");
        statement.setString(3, "%" + keyword + "%");
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Search results:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-10s | %-8s | %-10s\n",
                "CarID", "Model", "Company", "MakeYear", "Colour", "UserEmail");
        System.out.println("----------------------------------------------------------");
        while (resultSet.next()) {
            int carID = resultSet.getInt("CarID");
            String model = resultSet.getString("Model");
            String company = resultSet.getString("Company");
            int makeYear = resultSet.getInt("MakeYear");
            String colour = resultSet.getString("Colour");
            String userEmail = resultSet.getString("UserEmail");

            System.out.printf("%-5d | %-15s | %-15s | %-10d | %-8s | %-10s\n",
                    carID, model, company, makeYear, colour, userEmail);
        }
        System.out.println("----------------------------------------------------------");
    }

    private static void addCar() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter car details:");
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Company: ");
        String company = scanner.nextLine();
        System.out.print("Make Year: ");
        int makeYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Car Type (Enter 1 for Hatchback , 2 for Sedan , 3 for SUV and 4 for MPV): ");
        String carType = scanner.nextLine();
        System.out.print("Fuel Type (Enter 1 for Petrol , 2 for Diesel and 3 for Electric): ");
        String fuelType = scanner.nextLine();
        System.out.print("Transmission (Enter 1 for Manual and 2 for Automatic): ");
        String transmission = scanner.nextLine();
        System.out.print("RC No: ");
        String rcNo = scanner.nextLine();
        System.out.print("Insurance: ");
        String insurance = scanner.nextLine();
        System.out.print("Engine Power: ");
        int enginePower = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Colour: ");
        String colour = scanner.nextLine();

        String query = "INSERT INTO Car (Model, Company, MakeYear, CarTypeID, FuelTypeID, TransmissionID, RCNo, Insurance, UserEmail, EnginePower, Colour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, model);
        statement.setString(2, company);
        statement.setInt(3, makeYear);
        statement.setString(4, carType);
        statement.setString(5, fuelType);
        statement.setString(6, transmission);
        statement.setString(7, rcNo);
        statement.setString(8, insurance);
        statement.setString(9, currentUserEmail);
        statement.setInt(10, enginePower);
        statement.setString(11, colour);
        statement.executeUpdate();

        System.out.println("Car added successfully.");
    }

    private static void deleteCar() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CarID to delete: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "DELETE FROM Car WHERE CarID = ? AND UserEmail = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, carId);
        statement.setString(2, currentUserEmail);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Car deleted successfully.");
        } else {
            System.out.println("You are not authorized to delete this car or the car does not exist.");
        }
    }

    private static void showAllCars() throws SQLException {
        String query = "SELECT * FROM Car";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("List of all cars available:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-10s | %-8s | %-10s\n",
                "CarID", "Model", "Company", "MakeYear", "Colour", "UserEmail");
        System.out.println("----------------------------------------------------------");
        while (resultSet.next()) {
            int carID = resultSet.getInt("CarID");
            String model = resultSet.getString("Model");
            String company = resultSet.getString("Company");
            int makeYear = resultSet.getInt("MakeYear");
            String colour = resultSet.getString("Colour");
            String userEmail = resultSet.getString("UserEmail");

            System.out.printf("%-5d | %-15s | %-15s | %-10d | %-8s | %-10s\n",
                    carID, model, company, makeYear, colour, userEmail);
        }
        System.out.println("----------------------------------------------------------");
    }
}
