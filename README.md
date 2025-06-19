Contact Book Manager (Java Swing, MySQL, JDBC)
Welcome to the Contact Book Manager! This project is a robust, intuitive, and feature-rich desktop application designed to effortlessly manage your personal and professional contacts. It serves as a practical, hands-on demonstration of building a full-stack desktop application using industry-standard Java technologies.

✨ Core Features & Functionality
This application provides a complete set of CRUD (Create, Read, Update, Delete) operations for your contacts:

Add New Contacts: Easily input and save new contact entries including name, phone number, and email address.

View All Contacts: Display all your stored contacts in a clear, sortable, and interactive table within the GUI.

Update Existing Contacts: Select any contact from the table to automatically populate the input fields, allowing for quick and seamless modification of their details.

Delete Contacts: Permanently remove unwanted contacts from your database with a confirmation prompt.

In-Memory Management: Utilizes Java's ArrayList to maintain an efficient, responsive in-memory cache of contacts, synchronized seamlessly with the persistent storage.

Robust Data Persistence: All your contact information is securely stored and retrieved from a MySQL relational database, ensuring data integrity and availability across sessions.

Intuitive User Interface: A clean, user-friendly graphical interface built with Java Swing, providing a smooth and accessible experience for managing your contacts.

🚀 Architectural Layers & Technologies
The project is structured into distinct, logical layers to ensure modularity, maintainability, and a clear separation of concerns:

GUI Layer (com.contactbook.gui):

Java Swing: Responsible for all visual components (windows, buttons, text fields, tables) and handling user interactions.

Service Layer (com.contactbook.service):

Java Business Logic: Acts as an intermediary between the GUI and the database layer. It manages the in-memory ArrayList of Contact objects and orchestrates data flow to and from the database.

Database Layer (com.contactbook.database):

JDBC (Java Database Connectivity): Provides the standard API for connecting to and performing SQL operations (INSERT, SELECT, UPDATE, DELETE) on the MySQL database.

MySQL: The chosen relational database management system for persistent data storage.

Model Layer (com.contactbook.model):

Contact.java: A simple Plain Old Java Object (POJO) representing the structure of a single contact record.

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
│   └── mysql-connector-j-9.3.0.jar   # MySQL JDBC Driver (Version may vary)
├── .project                            # Eclipse project metadata
├── .classpath                          # Eclipse build path definition
├── .settings/                          # Eclipse specific project settings
└── ... (other generated files)

🛠️ Setup and Installation
To get this project up and running on your local machine, follow these steps:

1. Database Setup (MySQL)
You must have a MySQL server installed and running. Then, execute the following SQL commands in your MySQL client (e.g., MySQL Workbench, MySQL Command Line Client, or phpMyAdmin) to create the necessary database and table:

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS connect_db;

-- Switch to the newly created database
USE connect_db;

-- Create the 'contacts' table
CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255)
);

Important Note: The Java application assumes the database and table exist. It does not create them automatically.

2. Configure Database Credentials in Java
Open the src/com/contactbook/database/DatabaseManager.java file in your IDE (Eclipse). Locate the following lines and ensure the DB_USER and DB_PASSWORD constants match your actual MySQL login credentials.

public class DatabaseManager {
    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/connect_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";         // <<<--- YOUR MySQL username
    private static final String DB_PASSWORD = "Kgisl@123"; // <<<--- YOUR MySQL password
    // ... rest of the class
}

For production deployments, it's highly recommended to use a dedicated database user with minimal necessary permissions rather than root, and to manage credentials securely (e.g., via environment variables).

3. Eclipse Project Setup
Clone or Download: Obtain the project source code and place it in your desired workspace.

Import into Eclipse:

Open Eclipse.

Go to File > Import....

Select General > Existing Projects into Workspace > Next.

Click Browse... and navigate to the root directory of this project (ContactBookManager).

Ensure "Copy projects into workspace" is checked if you want to move the project files.

Click Finish.

Add MySQL JDBC Driver to Build Path:

Download the MySQL Connector/J JAR (e.g., mysql-connector-j-9.3.0.jar) from the official MySQL website. Choose the "Platform Independent" .zip or .tar.gz and extract the .jar file.

In Eclipse's Package Explorer, right-click on your ContactBookManager project and select New > Folder. Name it lib and click Finish.

Copy the downloaded mysql-connector-j-9.3.0.jar file from your download location and paste it into the newly created lib folder within your Eclipse project structure (e.g., ContactBookManager/lib).

In Eclipse, right-click on your ContactBookManager project > Properties > Java Build Path > Libraries tab.

Click the Add JARs... button.

In the "JAR Selection" dialog, navigate through your project: ContactBookManager > lib > mysql-connector-j-9.3.0.jar. Select it and click OK.

Click Apply and then OK to close the Properties window.

Finally, in the Eclipse menu, go to Project > Clean..., select ContactBookManager, and click Clean. Then, go to Project > Build Project. This ensures all changes are recognized.

▶️ How to Run the Application
Once the setup is complete, you can run the application in several ways:

Directly from Eclipse:

In the Package Explorer, navigate to src/com/contactbook/gui/ContactBookGUI.java.

Right-click on ContactBookGUI.java.

Select Run As > Java Application. The GUI window should appear.

As a Runnable JAR File:

In Eclipse, go to File > Export....

Expand Java and select Runnable JAR file. Click Next.

In the "Launch configuration:" dropdown, choose ContactBookGUI - ContactBookManager.

Select a suitable "Export destination" (e.g., a dist folder within your project).

Under "Library handling:", select Package required libraries into generated JAR.

Click Finish.

Open your system's command prompt/terminal, navigate to the folder where you saved the .jar file (e.g., your_project/dist), and execute:

java -jar ContactBookManager.jar

As a Standalone Executable (.exe - Windows Specific):

For a more native Windows experience, you can wrap the generated ContactBookManager.jar into an .exe file using a third-party tool like Launch4j.

Download and install Launch4j.

Open Launch4j and configure it by pointing to your ContactBookManager.jar as the input, specifying the output .exe name, and optionally bundling a JRE for standalone distribution. This creates a single executable file that users can double-click directly.

🤝 Contributing
Contributions are welcome! If you have suggestions for improvements, bug fixes, or new features, please feel free to:

Fork this repository.

Open an issue to discuss proposed changes or report bugs.

Submit a pull request with your enhancements.

Your input helps make this project better!
