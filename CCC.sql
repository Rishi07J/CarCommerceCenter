	create database car;
	use car;
	CREATE TABLE Car (
		CarID INT PRIMARY KEY,
		Model VARCHAR(255),
		Company VARCHAR(255),
		MakeYear INT,
		CarType VARCHAR(50),
		FuelType VARCHAR(50),
		Transmission VARCHAR(50),
		RCNo VARCHAR(50),
		Insurance VARCHAR(50),
		UserEmail VARCHAR(255),
		EnginePower INT,
		Colour VARCHAR(50)
	);

	CREATE TABLE User (
		Email VARCHAR(255) PRIMARY KEY,
		Password VARCHAR(255),
		PhoneNo VARCHAR(20)
	);

	CREATE TABLE Seller (
		AadharNo VARCHAR(20) PRIMARY KEY,
		Name VARCHAR(255),
		Address VARCHAR(255),
		PhoneNo VARCHAR(20),
		DOB DATE,
		Email VARCHAR(255),
		DLNo VARCHAR(50)
	);

	CREATE TABLE Buyer (
		AadharNo VARCHAR(20) PRIMARY KEY,
		Name VARCHAR(255),
		Address VARCHAR(255),
		PhoneNo VARCHAR(20),
		DOB DATE,
		Email VARCHAR(255),
		DLNo VARCHAR(50)
	);

	CREATE TABLE Orders (
		OrderID INT PRIMARY KEY,
		CarID INT,
		UserEmail VARCHAR(255),
		SellerAadharNo VARCHAR(20),
		BuyerAadharNo VARCHAR(20),
		OrderDate DATE,
		Price DECIMAL(10, 2)
	);



CREATE TABLE CarType (
    CarTypeID INT PRIMARY KEY,
    Type VARCHAR(50)
);

CREATE TABLE FuelType (
    FuelTypeID INT PRIMARY KEY,
    FuelType VARCHAR(50)
);

CREATE TABLE Transmission (
    TransmissionID INT PRIMARY KEY,
    Transmission VARCHAR(50)
);


ALTER TABLE Car
ADD COLUMN CarTypeID INT,
ADD COLUMN FuelTypeID INT,
ADD COLUMN TransmissionID INT,
ADD FOREIGN KEY (CarTypeID) REFERENCES CarType(CarTypeID),
ADD FOREIGN KEY (FuelTypeID) REFERENCES FuelType(FuelTypeID),
ADD FOREIGN KEY (TransmissionID) REFERENCES Transmission(TransmissionID);

-- Drop redundant columns from the Car table
ALTER TABLE Car
DROP COLUMN CarType,
DROP COLUMN FuelType,
DROP COLUMN Transmission;


-- Populate CarType table
INSERT INTO CarType (CarTypeID, Type)
VALUES (1, 'Hatchback'),
       (2, 'Sedan'),
       (3, 'SUV'),
       (4, 'MPV');

-- Populate FuelType table
INSERT INTO FuelType (FuelTypeID, FuelType)
VALUES (1, 'Petrol'),
       (2, 'Diesel'),
       (3, 'Electric');

-- Populate Transmission table
INSERT INTO Transmission (TransmissionID, Transmission)
VALUES (1, 'Manual'),
       (2, 'Automatic');

-- Populate User table
INSERT INTO User (Email, Password, PhoneNo)
VALUES ('user1@gmail.com', 'password1', '9876543210'),
       ('user2@gmail.com', 'password2', '9876543211'),
       ('user3@gmail.com', 'password3', '9876543212'),
       ('user4@gmail.com', 'password4', '9876543213'),
       ('user5@gmail.com', 'password5', '9876543214');

-- Populate Seller table
INSERT INTO Seller (AadharNo, Name, Address, PhoneNo, DOB, Email, DLNo)
VALUES ('123456789012', 'Ramesh Sharma', '123, ABC Street, XYZ City', '9876543215', '1980-05-10', 'seller1@gmail.com', 'DL123456'),
       ('234567890123', 'Suresh Kumar', '456, DEF Street, XYZ City', '9876543216', '1975-08-20', 'seller2@gmail.com', 'DL234567'),
       ('345678901234', 'Priya Singh', '789, GHI Street, XYZ City', '9876543217', '1988-02-15', 'seller3@gmail.com', 'DL345678'),
       ('456789012345', 'Neha Patel', '012, JKL Street, XYZ City', '9876543218', '1992-11-30', 'seller4@gmail.com', 'DL456789'),
       ('567890123456', 'Amit Verma', '345, MNO Street, XYZ City', '9876543219', '1983-07-25', 'seller5@gmail.com', 'DL567890');

-- Populate Buyer table
INSERT INTO Buyer (AadharNo, Name, Address, PhoneNo, DOB, Email, DLNo)
VALUES ('678901234567', 'Anjali Gupta', '901, PQR Street, XYZ City', '9876543220', '1986-04-05', 'buyer1@gmail.com', 'DL678901'),
       ('789012345678', 'Manoj Reddy', '234, STU Street, XYZ City', '9876543221', '1990-09-15', 'buyer2@gmail.com', 'DL789012'),
       ('890123456789', 'Shalini Shah', '567, VWX Street, XYZ City', '9876543222', '1982-12-25', 'buyer3@gmail.com', 'DL890123'),
       ('901234567890', 'Rajesh Singhania', '890, YZA Street, XYZ City', '9876543223', '1978-06-20', 'buyer4@gmail.com', 'DL901234'),
       ('012345678901', 'Deepak Sharma', '123, BCD Street, XYZ City', '9876543224', '1995-03-10', 'buyer5@gmail.com', 'DL012345');

-- Populate Car table
INSERT INTO Car (CarID, Model, Company, MakeYear, CarTypeID, FuelTypeID, TransmissionID, RCNo, Insurance, UserEmail, EnginePower, Colour)
VALUES (1, 'Maruti Swift', 'Maruti Suzuki', 2019, 1, 1, 1, 'RC1234', 'Yes', 'seller1@gmail.com', 1200, 'Red'),
       (2, 'Hyundai i20', 'Hyundai', 2020, 1, 1, 2, 'RC2345', 'Yes', 'seller2@gmail.com', 1400, 'Blue'),
       (3, 'Honda City', 'Honda', 2021, 2, 1, 2, 'RC3456', 'Yes', 'seller3@gmail.com', 1500, 'White'),
       (4, 'Mahindra XUV500', 'Mahindra', 2018, 3, 2, 2, 'RC4567', 'Yes', 'seller4@gmail.com', 2000, 'Black'),
       (5, 'Toyota Innova Crysta', 'Toyota', 2020, 4, 2, 1, 'RC5678', 'Yes', 'seller5@gmail.com', 2200, 'Silver');

-- Populate Orders table
INSERT INTO Orders (OrderID, CarID, UserEmail, SellerAadharNo, BuyerAadharNo, OrderDate, Price)
VALUES (1, 1, 'buyer1@gmail.com', '123456789012', '678901234567', '2024-03-20', 650000),
       (2, 2, 'buyer2@gmail.com', '234567890123', '789012345678', '2024-03-21', 750000),
       (3, 3, 'buyer3@gmail.com', '345678901234', '890123456789', '2024-03-22', 850000),
       (4, 4, 'buyer4@gmail.com', '456789012345', '901234567890', '2024-03-23', 950000),
       (5, 5, 'buyer5@gmail.com', '567890123456', '012345678901', '2024-03-24', 1050000);
       

ALTER TABLE Car DROP PRIMARY KEY;
ALTER TABLE Car MODIFY COLUMN CarID INT AUTO_INCREMENT PRIMARY KEY;




