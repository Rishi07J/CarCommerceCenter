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
        
        int keywordChoice;
        while (true) {
            System.out.println("Select search option:");
            System.out.println("1. Model");
            System.out.println("2. Company");
            System.out.println("3. Colour");
            System.out.print("Enter your choice: ");
            try {
                keywordChoice = Integer.parseInt(scanner.nextLine());
                if (keywordChoice >= 1 && keywordChoice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            }
        }
        
        String keywordName = "";
        switch (keywordChoice) {
            case 1:
                keywordName = "Model";
                break;
            case 2:
                keywordName = "Company";
                break;
            case 3:
                keywordName = "Colour";
                break;
        }

        System.out.print("Enter search keyword for " + keywordName + ": ");
        String keyword = getNonEmptyInput(scanner, "Search Keyword");

        String query = "SELECT * FROM Car WHERE " + keywordName + " LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + keyword + "%");
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Search results:");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-10s | %-8s | %-10s\n",
                "CarID", "Model", "Company", "MakeYear", "Colour", "UserEmail");
        System.out.println("---------------------------------------------------------------------------------------");
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
        String model = getNonEmptyInput(scanner, "Model");
        System.out.print("Company: ");
        String company = getNonEmptyInput(scanner, "Company");
        System.out.print("Make Year: ");
        int makeYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Car Type (Enter 1 for Hatchback , 2 for Sedan , 3 for SUV and 4 for MPV): ");
        String carType = getNonEmptyInput(scanner, "Car Type");
        System.out.print("Fuel Type (Enter 1 for Petrol , 2 for Diesel and 3 for Electric): ");
        String fuelType = getNonEmptyInput(scanner, "Fuel Type");
        System.out.print("Transmission (Enter 1 for Manual and 2 for Automatic): ");
        String transmission = getNonEmptyInput(scanner, "Transmission");
        System.out.print("RC No: ");
        String rcNo = getNonEmptyInput(scanner, "RC No");
        System.out.print("Insurance: ");
        String insurance = getNonEmptyInput(scanner, "Insurance");
        System.out.print("Engine Power: ");
        int enginePower = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Colour: ");
        String colour = getNonEmptyInput(scanner, "Colour");

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
        int carId;

        while (true) {
            System.out.print("Enter CarID to delete: ");
            String input = getNonEmptyInput(scanner, "CarID");
            try {
                carId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. CarID must be a valid integer.");
            }
        }

        String query = "DELETE FROM Car WHERE CarID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, carId);

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
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-10s | %-8s | %-10s\n",
                "CarID", "Model", "Company", "MakeYear", "Colour", "UserEmail");
        System.out.println("---------------------------------------------------------------------------------------------");
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
    
    private static String getNonEmptyInput(Scanner scanner, String inputName) {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println(inputName + " cannot be empty. Please enter again:");
            }
        }
    }
}
