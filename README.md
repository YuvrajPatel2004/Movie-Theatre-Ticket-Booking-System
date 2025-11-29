# 🎬 Movie & Theatre Ticket Booking System (CLI Based)

A command-line based **Movie & Theatre Ticket Booking System** developed using **Java, JDBC, and MySQL**.  
This project emulates real-world movie ticket platforms like BookMyShow and PVR, focusing on backend architecture, database design, and business logic.

---

## 📌 Project Description

This application allows users to:
- Browse available movies
- View show timings and screens
- Select seats
- Book tickets
- Make payments
- View booking history
- Cancel bookings and get refunds

Administrators can:
- Manage movies
- Configure halls and screens
- Create shows and timings
- Monitor revenue reports
- Track ticket statistics

The application runs completely on the Command Line Interface (CLI), making it lightweight and suitable for academic use.

---

## 🎯 Objectives

- Simulate a real-world ticket booking system
- Implement database connectivity using JDBC
- Apply Object-Oriented Programming concepts
- Practice modular architecture (DAO pattern)
- Handle transactions, payments, and refunds
- Provide revenue analytics
- Build a scalable system for future expansion

---

## 🏗 System Architecture

The system follows a **4-layer design**:

### 1. Model Layer
Handles data representation:
- User
- Admin
- Movie
- Hall
- Screen
- Seat
- Show
- Booking
- Ticket
- Payment

---

### 2. DAO Layer
Handles database operations:
- UserDAO
- AdminDAO
- MovieDAO
- HallDAO
- ScreenDAO
- ShowDAO
- SeatDAO
- BookingDAO
- TicketDAO
- PaymentDAO

---

### 3. Utility Layer
Contains:
- `DBUtil.java` for MySQL connection and credential management

---

### 4. Controller Layer
- `Main.java` handles:
  - Menu navigation
  - Login & Registration
  - Booking flow
  - Payments
  - Refund handling
  - Revenue reporting

---

## 🚀 Features

### ✅ User Module
- Register & Login
- Browse movies
- View shows and timings
- Seat selection
- Ticket booking
- Payment processing
- Booking history
- Booking cancellation
- Refund calculation

---

### ✅ Admin Module
- Add/Delete movies
- Create halls
- Add screens
- Generate seats automatically
- Schedule shows
- View reports
- Revenue statistics

---

## 💳 Refund Policy Logic

| Time Before Show | Refund |
|------------------|--------|
| ≥ 24 hours        | 100%   |
| 3 – 24 hours      | 50%    |
| < 3 hours         | 0%     |

Refund transactions are stored as negative values in the payments table.

---

## 🗄 Database Tables

### Core Tables
- `users`
- `admins`
- `movies`
- `halls`
- `screens`
- `seats`
- `shows`
- `bookings`
- `tickets`
- `payments`

---

### Relationships
- One Hall → Many Screens  
- One Screen → Many Seats  
- One Movie → Many Shows  
- One Booking → Many Tickets  
- One Booking → Many Payments  

---

## ⚙ Tech Stack

### Backend
- Java (JDK 8+)
- JDBC
- MySQL

### Tools
- Git & GitHub
- MySQL Workbench / CLI
- Eclipse / IntelliJ / VS Code
- Linux Terminal / Command Prompt

---

## 🖥 System Requirements

### Hardware
- Processor: Dual core minimum
- RAM: 2 GB (4 GB recommended)
- Storage: 200 MB free

### Software
- Java JDK 8+
- MySQL Server 8+
- MySQL Connector/J
- Any terminal or shell

---

## ▶️ How To Run

### 1. Create Database
```sql
CREATE DATABASE movie_booking_system;
USE movie_booking_system;
```
# ⚙ Configuration, Build & Execution Guide

---

## 2. Configure Database Connection

Edit the `DBUtil.java` file and update your database credentials:

```java
String url = "jdbc:mysql://localhost:3306/movie_booking_system";
String user = "root";
String password = "yourPassword";
```
## 3. Compile Program
```java
javac -cp "lib/*" -d out src/**/*.java
```
## 4. Run Program
```java
java -cp "out:lib/*" com.moviebooking.Main
```

## 📈 Future Scope

- Web Application (React + Spring Boot)
- Mobile App Integration
- Online Payment Gateway
- Email / SMS Notifications
- QR-Code Based Tickets
- Dynamic Seat Pricing
- Multi-City Theatres Support
- Cloud Deployment
- Data Analytics Dashboard
- AI-Based Recommendations

---

## 📚 References

- Oracle JDBC Documentation
- MySQL Official Documentation
- Herbert Schildt – *Java: The Complete Reference*
- Database Systems – Connolly & Begg
- GeeksForGeeks
- StackOverflow

---

## 🏷 Project Category

- Database Management System
- Core Java Project
- JDBC Application
- Software Engineering
- Console Application
