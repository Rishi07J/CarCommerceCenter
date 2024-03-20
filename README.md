Car Commerce Center

This project implements a simple car commerce center system using Java and MySQL. It allows users to perform various operations related to buying and selling cars. The system provides functionalities such as adding cars for sale, deleting cars, searching for cars, and displaying all available cars. It includes features like user authentication, SQL database management, and JDBC integration.

SQL Database Schema
The SQL database schema consists of several tables:
Car: Stores information about cars, including their model, company, make year, car type, fuel type, transmission, RC number, insurance, engine power, color, and owner's email.
User: Contains user authentication details like email, password, and phone number.
Seller and Buyer: Stores details of sellers and buyers including their Aadhar number, name, address, phone number, date of birth, email, and driving license number.
Orders: Records details of car orders including the order ID, car ID, user email, seller Aadhar number, buyer Aadhar number, order date, and price.
CarType, FuelType, and Transmission: Store predefined types for car type, fuel type, and transmission respectively.

Java Code
The Java code implements the main functionalities of the car commerce center:
Login: Users can log in using their email and password.
Add Car: Sellers can add cars for sale by providing details like model, company, make year, car type, fuel type, transmission, RC number, insurance, engine power, and color.
Delete Car: Sellers can delete their cars from the system using the car ID.
Search Cars: Users can search for cars based on model, company, or color.
Show All Cars: Displays a list of all cars available for sale.

Setup Instructions
Ensure MySQL is installed on your system.
Create a MySQL database named car.
Execute the SQL script provided to create tables and insert sample data into the database.
Import the Java code into your IDE and ensure the MySQL Connector/J library is added to the project.
Update the database URL, username, and password in the Java code if necessary.
Run the CarCommerceCenter.java file to start the application.

Additional Resources
ER Diagram: An Entity-Relationship (ER) diagram is available in the repository, providing a visual representation of the database schema and relationships between tables.
Report: A detailed report documenting the project's objectives, methodology, implementation, and results is also included in the repository.

Technologies Used
Java
MySQL
JDBC

Key Features
User authentication
CRUD operations for cars
Integration with MySQL database
Search functionality for cars

Contributors
Rishi Joshi
Mayank Bhardwaj
