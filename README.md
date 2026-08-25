# 📚 Bookstore Application

A JavaFX-based desktop bookstore application developed to simulate the operations of a small bookstore. The application provides separate functionality for **customers and administrators**, including user authentication, book browsing, purchasing, inventory management, customer management, and a points-based rewards system.

## ✨ Features

### 👤 Customer Features

* Customer login authentication
* Browse available books
* Select books for purchase
* Purchase books using regular payment
* Purchase books using accumulated points
* Earn points through purchases
* Track accumulated reward points
* Customer status system:

  * **SILVER**
  * **GOLD**

### 👨‍💼 Administrator Features

* Administrator login
* View available books
* Manage bookstore inventory
* View registered customers
* Manage customer information
* Restock bookstore data
* Navigate between book and customer management screens

### ⭐ Rewards System

The application includes a points-based customer rewards system.

Customers begin with **0 points** and their status is determined by their accumulated points:

```text
0 – 1000 points     → SILVER
1001+ points        → GOLD
```

The customer class automatically updates the customer's status whenever their points change.

---

## 🧠 Application Design

The application is organized into several Java classes, each responsible for a specific part of the bookstore system.

```text
┌─────────────────────────┐
│          Main           │
│   JavaFX Application    │
└────────────┬────────────┘
             │
     ┌───────┴────────┐
     │                │
     ▼                ▼
┌───────────┐    ┌────────────┐
│ Customer  │    │   Owner    │
└─────┬─────┘    └──────┬─────┘
      │                 │
      ▼                 ▼
┌───────────┐      ┌──────────┐
│   Book    │      │  Books   │
└───────────┘      │ Customers│
                   └────┬─────┘
                        │
                        ▼
                   ┌─────────┐
                   │  Files  │
                   └─────────┘
```

### Main Components

| Class      | Responsibility                                                       |
| ---------- | -------------------------------------------------------------------- |
| `Main`     | JavaFX application, screens, navigation, login and user interactions |
| `Book`     | Represents a bookstore book and its price                            |
| `Customer` | Stores customer credentials, points, and membership status           |
| `Owner`    | Manages bookstore owner/admin data, books, and customers             |
| `Files`    | Handles reading and writing bookstore data to files                  |

---

## 🛠️ Technologies

* **Java**
* **JavaFX**
* **Object-Oriented Programming**
* **File I/O**
* **ArrayList**
* **JavaFX TableView**
* **JavaFX GUI Components**
* **NetBeans**

---

## 📂 Project Structure

```text
Bookstore-Application/
│
├── src/
│   ├── bookstore/
│   │   ├── Main.java
│   │   ├── Book.java
│   │   ├── Customer.java
│   │   ├── Owner.java
│   │   └── Files.java
│   │
│   └── kidReading.png
│
├── book.txt
├── customer.txt
├── Project_Diagrams.vpp
├── build.xml
├── manifest.mf
│
└── nbproject/
    ├── project.xml
    └── project.properties
```

---

## 💾 Data Storage

The application uses local text files to persist bookstore information.

### `book.txt`

Stores book information including:

```text
Book Title, Price
```

### `customer.txt`

Stores customer information including:

```text
Username, Password, Points
```

The `Files` class provides functionality to:

* Read books from `book.txt`
* Write books to `book.txt`
* Reset the book file
* Read customers from `customer.txt`
* Write customers to `customer.txt`
* Reset the customer file

When the application starts, existing book and customer data is loaded into memory.

---

## 🔐 Login System

The application supports two types of users:

### Administrator

The administrator can access:

* Book management
* Customer management
* Inventory information

### Customer

Customers can access:

* Their bookstore home screen
* Available books
* Purchasing functionality
* Points-based purchases

The application determines the appropriate interface based on the credentials entered during login.

---

## 🖥️ User Interface

The application is built using **JavaFX** and includes multiple screens for different operations.

The main navigation includes:

```text
Login
  │
  ├── Administrator
  │      │
  │      ├── Books
  │      └── Customers
  │
  └── Customer
         │
         ├── Browse Books
         ├── Purchase Books
         └── Purchase Using Points
```

The application uses JavaFX components such as:

* `TableView`
* `Button`
* `TextField`
* `PasswordField`
* `Label`
* `CheckBox`
* `Scene`
* `Stage`
* `HBox`
* `VBox`

---

## 🚀 Getting Started

### Requirements

* **Java JDK 19**
* **JavaFX**
* **NetBeans** or another Java IDE configured for JavaFX
* Git

The project is configured to compile using **Java 19**.

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/bookstore-application.git
cd bookstore-application
```

### Open the Project

Open the project using **NetBeans**.

The project includes NetBeans configuration files under:

```text
nbproject/
```

### Run the Application

Run the `Main.java` class:

```text
src/bookstore/Main.java
```

Alternatively, run the project directly through NetBeans.

---

## 🔄 Application Workflow

### Customer Workflow

```text
Login
  ↓
Customer Home
  ↓
Browse Available Books
  ↓
Select Books
  ↓
Choose Purchase Method
  ├── Regular Purchase
  └── Points Purchase
  ↓
Update Customer Points
  ↓
Update Customer Status
```

### Administrator Workflow

```text
Login
  ↓
Administrator Dashboard
  ├── View Books
  │     └── Manage Inventory
  │
  └── View Customers
        └── Manage Customer Information
```

---

## 🎯 Project Objectives

The main objectives of this project were to:

* Develop a functional desktop bookstore application.
* Apply **object-oriented programming principles**.
* Build a graphical user interface using JavaFX.
* Implement user authentication.
* Manage books and customer records.
* Implement a customer rewards/points system.
* Persist application data using file I/O.
* Practice software design and application architecture.

---

## 📐 Software Design

The project also includes software design diagrams in:

```text
Project_Diagrams.vpp
```

These diagrams were created to document the structure and design of the bookstore application.

---

## 📚 Key Concepts Demonstrated

This project demonstrates practical experience with:

* Object-Oriented Programming
* Classes and Objects
* Encapsulation
* Inheritance/role-based application design
* Collections and `ArrayList`
* File Input/Output
* Exception Handling
* GUI Development
* Event-Driven Programming
* JavaFX Scene Management
* Data Persistence
* Application State Management

---

## 🔮 Future Improvements

Potential improvements to the application include:

* Replace text-file storage with a relational database.
* Add secure password hashing instead of storing passwords as plain text.
* Implement a more complete inventory management system.
* Add shopping cart functionality.
* Add purchase history for customers.
* Add book search and filtering.
* Add book categories and authors.
* Improve the rewards system with additional membership tiers.
* Add automated unit and integration tests.
* Improve the UI with modern JavaFX styling.
* Add proper authentication and authorization.

---

## 👤 Author

**Chanuth Pathirana**

Computer Engineering — Software Engineering
Toronto Metropolitan University

---

## 📄 Project Information

This project was developed as an academic software development project and demonstrates the design and implementation of a JavaFX-based bookstore management system.
