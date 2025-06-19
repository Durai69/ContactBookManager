Contact Book Manager (Java Swing, MySQL, JDBC)
A simple yet robust Contact Book Management application built with Java Swing for the graphical user interface, MySQL for persistent data storage, and JDBC for database connectivity. This project demonstrates fundamental concepts of desktop application development, database integration, and CRUD (Create, Read, Update, Delete) operations.

✨ Features
Add New Contacts: Easily add new contact entries with name, phone number, and email.

View All Contacts: Display all stored contacts in a clean, interactive table.

Update Existing Contacts: Select a contact from the table to populate fields for easy modification.

Delete Contacts: Remove contacts from the database and the displayed list.

In-Memory Management: Uses ArrayList for quick in-memory access and updates, synchronized with the database.

Persistent Storage: All contact data is stored securely in a MySQL database.

User-Friendly GUI: Intuitive Swing interface for smooth user interaction.

🚀 Technologies Used
Frontend: Java Swing (GUI)

Backend/Logic: Java

Database: MySQL

Database Connectivity: JDBC (Java Database Connectivity)

📦 Project Structure
ContactBookManager/
├── src/
│   └── com/
│       └── contactbook/
│           ├── model/
│           │   └── Contact.java        # Data model for a Contact
│           ├── database/
│           │   └── DatabaseManager.java  # Handles all MySQL (JDBC) operations
│           ├── service/
│           │   └── ContactService.java   # Manages in-memory contacts and interacts with DatabaseManager
│           └── gui/
│               └── ContactBookGUI.java # The main Swing GUI class
├── lib/
│   └── mysql-connector-j-9.3.0.jar   # MySQL JDBC Driver
└── ... (other Eclipse/project files like .project, .classpath, .settings)

🛠️ Setup and Installation
1. Database Setup (MySQL)
Ensure your MySQL server is running. Then, execute the following SQL commands in your MySQL client (e.g., MySQL Workbench, command line):

-- Create the database
CREATE DATABASE connect_db;

-- Use the newly created database
USE connect_db;

-- Create the contacts table
CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255)
);

2. Configure Database Credentials
Open src/com/contactbook/database/DatabaseManager.java and update the database credentials:

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/connect_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";         // Your MySQL username
    private static final String DB_PASSWORD = "Kgisl@123"; // Your MySQL password
    // ... rest of the class
}

Note: Always use strong, unique passwords and consider creating a dedicated database user with minimal necessary permissions for production environments.

3. Eclipse Project Setup
Clone or Download: Get the project files into your local machine.

Import into Eclipse: In Eclipse, go to File > Import... > General > Existing Projects into Workspace > Next. Browse to your project root directory and click Finish.

Add MySQL JDBC Driver:

Download the mysql-connector-j-9.3.0.jar (or latest compatible version) from the official MySQL website.

Create a lib folder inside your ContactBookManager project in Eclipse.

Copy the downloaded mysql-connector-j-9.3.0.jar into this lib folder.

In Eclipse, right-click on your ContactBookManager project > Properties > Java Build Path > Libraries tab.

Click Add JARs..., navigate to ContactBookManager/lib/mysql-connector-j-9.3.0.jar, select it, and click OK.

Click Apply and OK.

Go to Project > Clean... > Clean. Then Project > Build Project.

▶️ How to Run
From Eclipse:

Right-click src/com/contactbook/gui/ContactBookGUI.java in the Package Explorer.

Select Run As > Java Application.

As a Runnable JAR:

Export the project as a Runnable JAR: File > Export... > Java > Runnable JAR file.

Select ContactBookGUI - ContactBookManager as the launch configuration and choose Package required libraries into generated JAR.

Save it (e.g., ContactBookManager.jar).

Open a command prompt, navigate to where you saved the JAR, and run:

java -jar ContactBookManager.jar

As an Executable (.exe - Windows Only):

Use a tool like Launch4j to wrap the ContactBookManager.jar into a native Windows .exe file. You can optionally bundle a JRE with it.

🤝 Contributing
Feel free to fork this repository, open issues, or submit pull requests if you have suggestions for improvements or bug fixes!
