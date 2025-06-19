# 📒 Contact Book Manager

**Java Swing | MySQL | JDBC**

Welcome to **Contact Book Manager** — a robust and intuitive desktop application designed for effortlessly managing personal and professional contacts. Built using standard Java technologies, this project serves as a comprehensive, hands-on demonstration of how to architect a full-stack desktop application.

---

## ✨ Features

This application provides a full suite of **CRUD (Create, Read, Update, Delete)** operations within an intuitive GUI:

* **➕ Add New Contacts**
  Quickly add new entries including name, phone number, and email.

* **👀 View Contacts**
  Browse all contacts in a sortable, searchable table view.

* **✏️ Edit Contacts**
  Click any row to auto-fill the form fields and update the contact.

* **🗑️ Delete Contacts**
  Remove unnecessary entries with a confirmation prompt.

* **⚡ In-Memory Management**
  Leverages Java’s `ArrayList` for fast, temporary in-memory storage synced with the database.

* **💾 Persistent Storage**
  Contact data is reliably stored and retrieved from a MySQL database using JDBC.

* **🖥️ User-Friendly Interface**
  A clean, responsive GUI built with Java Swing for an easy and seamless user experience.

---

## 🧱 Architecture & Technologies

The project is organized into a modular, layered architecture:

### 🔹 GUI Layer (`com.contactbook.gui`)

* **Java Swing** handles all UI components like windows, buttons, forms, and tables.

### 🔹 Service Layer (`com.contactbook.service`)

* Contains business logic, coordinates between the UI and the database, and manages the in-memory contact list.

### 🔹 Database Layer (`com.contactbook.database`)

* **JDBC** is used for executing SQL operations.
* **MySQL** is the backend RDBMS for storing all contact records.

### 🔹 Model Layer (`com.contactbook.model`)

* **Contact.java** is a POJO that represents a contact entity (name, phone, email).

---

## 📁 Project Structure

```plaintext
ContactBookManager/
├── src/
│   └── com/contactbook/
│       ├── model/
│       │   └── Contact.java
│       ├── database/
│       │   └── DatabaseManager.java
│       ├── service/
│       │   └── ContactService.java
│       └── gui/
│           └── ContactBookGUI.java
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── .project
├── .classpath
├── .settings/
└── ... (other IDE-generated files)
```

---

## ⚙️ Getting Started

### 1️⃣ MySQL Setup

Run the following SQL script in MySQL Workbench or another client:

```sql
CREATE DATABASE IF NOT EXISTS connect_db;
USE connect_db;

CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255)
);
```

> ⚠️ *Note: The application **assumes** the database and table already exist. It does not auto-create them.*

---

### 2️⃣ Configure Database Credentials

Open `DatabaseManager.java` and update your MySQL credentials:

```java
private static final String JDBC_URL = "jdbc:mysql://localhost:3306/connect_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "your_username";
private static final String DB_PASSWORD = "your_password";
```

> 🔐 *For production environments, avoid hardcoding credentials. Use environment variables or config files.*

---

### 3️⃣ Setup in Eclipse

* **Clone/Download** the project to your workspace.
* Open Eclipse → `File > Import...` → `Existing Projects into Workspace` → Select root folder → Finish.
* Add MySQL Connector:

  * Right-click project → New > Folder → Name it `lib`.
  * Paste `mysql-connector-j-9.3.0.jar` into `lib/`.
  * Right-click project → `Build Path > Configure Build Path...` → Libraries → Add JARs → Select the JAR.
* Clean and build the project:
  `Project > Clean... > Build Project`

---

## ▶️ Running the App

### ✅ Run via Eclipse

1. Navigate to `ContactBookGUI.java`.
2. Right-click → Run As → Java Application.

### 📦 Run as Runnable JAR

1. Export as Runnable JAR:

   * `File > Export... > Java > Runnable JAR file`
   * Choose launch config: `ContactBookGUI`
   * Library Handling: *Package required libraries*
   * Export destination: `dist/ContactBookManager.jar`
2. Run in terminal:

   ```bash
   java -jar ContactBookManager.jar
   ```

### 🪟 Optional: Convert to Windows `.exe`

For native Windows usage:

* Use **Launch4j** to wrap `.jar` as `.exe`.
* Optionally bundle a JRE for standalone deployment.

---

## 🤝 Contributions

Contributions are welcome and appreciated!
To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`)
3. Commit your changes.
4. Push to your fork.
5. Open a pull request.

For bugs, enhancements, or feature ideas, feel free to [open an issue](https://github.com/your-username/ContactBookManager/issues).

---

## 🙌 Acknowledgements

* Java Swing for the GUI framework.
* MySQL for reliable data storage.
* JDBC for seamless Java-to-SQL integration.
* Eclipse IDE for project development.

---

