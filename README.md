# 🍔 Restaurant Ordering System (Client-Server Architecture)

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-Client--Server-blue.svg)]()
[![UI](https://img.shields.io/badge/UI-Java%20Swing-green.svg)]()

A comprehensive, multi-client Restaurant Ordering System built from scratch in Java using **Socket Programming**. The system follows a robust **Client-Server architecture**, allowing multiple users to browse a dynamic menu (Food, Drinks, and Desserts), place orders in real-time, and generate finalized receipts.

The project offers a **Dual-UI Experience**, enabling clients to connect and order either through a modern Graphical User Interface (GUI) built with Java Swing or via a lightweight Command-Line Interface (CLI).

---

## ✨ Key Features

* **Multithreaded Server:** Handles multiple client connections concurrently using Java Threads (`ClientHandler`), ensuring smooth parallel processing.
* **Dual UI Modes:** * **GUI Mode (`Client_UI.java`):** A fully structured, interactive desktop interface. It features a side-by-side layout displaying the full menu on the left and a retro hacker-style console (green text on black background) on the right for real-time order status and receipts.
  * **CLI Mode (`Client.java`):** A terminal-based, lightweight console interface for quick interactive ordering via standard input/output.
* **OOP-Driven Architecture:** Strongly adheres to Object-Oriented Programming concepts (Abstraction, Inheritance, and Encapsulation) for highly maintainable and scalable code.
* **Real-time Synchronization & Calculations:** Instantly tracks added items, performs price lookups by ID, and calculates the total price dynamically.
* **Robust Exception Handling:** Fully protected against invalid user inputs (e.g., non-numeric product IDs, out-of-bound IDs) and handles abrupt client disconnections gracefully without crashing the server.

---

## 🛠️ Tech Stack & Concepts

* **Language:** Java (JDK 17 or higher)
* **Networking:** TCP/IP Sockets (`ServerSocket`, `Socket`)
* **Concurrency:** Java Multithreading (`Thread` class)
* **GUI Framework:** Java Swing & AWT (`JFrame`, `JTextArea`, `JSplitPane`, `FlowLayout`)
* **Data Structures:** `ArrayList` for dynamic menu and order management
* **Design Patterns/OOP:** Inheritance (`Food`, `Drink`, and `Dessert` inheriting from an abstract `Product` class)

---

## 📂 Project Structure & Class Responsibilities

| File Name | Description & Core Responsibility |
| :--- | :--- |
| **`Server.java`** | Establishes the main server on port `5000`. Contains the `ClientHandler` thread class which manages individual client sessions, streams the menu, processes order requests, and formats the final bill. |
| **`Client_UI.java`** | Implements the graphical desktop client. Manages UI components, updates the order logs dynamically, and sends actions over the network when buttons are clicked. |
| **`Client.java`**| Implements the alternative terminal-based client utilizing a standard Java `Scanner` loop to communicate with the server. |
| **`Product.class`** | The compiled bytecode representing the core abstract model for all items sold in the restaurant. |

---

## 📐 System Domain Model (OOP Design)

The system leverages a well-structured object hierarchy:
* **`Product` (Abstract Class):** Defines shared attributes (`id`, `name`, `price`) and abstract methods like `display()` and `calculate_price()`.
* **`Food`, `Drink`, `Dessert` (Subclasses):** Inherit from `Product` and encapsulate category-specific states (e.g., `spicy`, `with_sugar`, `size_is_big`).
* **`Menu`:** Pre-populates a curated list of 25 unique items across all categories with predefined prices in EGP.
* **`Order`:** Acts as a virtual shopping cart per client session, storing selected products and evaluating total sums.

---

## 🚀 Getting Started & How to Run

Follow these simple steps to run the network application on your local machine:

### 1. Prerequisites
Ensure you have the Java Development Kit (JDK) installed on your system.

### 2. Start the Server (Crucial Step First)
Compile and run the `Server.java` file. Your terminal will display:
```bash
--- Server is running on port 5000 ---
Waiting for clients...
