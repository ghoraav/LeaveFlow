# LeaveFlow: Automated Leave Management System 📅

LeaveFlow is a robust, Java-based web application designed to automate the process of leave applications and approvals within an organization. It replaces traditional paperwork with a seamless digital workflow for employees and administrators.

---

## 🌟 Key Features
* **Dual-Role Interface:** Separate dashboards for Employees (to apply for leave) and Administrators (to manage requests).
* **Real-Time Tracking:** Employees can view their leave balance and application status (Pending, Approved, Rejected).
* **Admin Controls:** Administrators have the authority to review, approve, or deny leave requests with a single click.
* **Database Integration:** Securely stores user profiles, leave history, and departmental data.
* **Responsive UI:** Clean and intuitive web interface designed for easy navigation.

---

## 🧪 Methodologies & Architecture
The project follows a **Model-View-Controller (MVC)** design pattern to ensure scalability and organized code:

* **Model:** Handles data logic and communication with the MySQL database using JDBC.
* **View:** Interactive frontend built with JSP (JavaServer Pages), CSS, and JavaScript.
* **Controller:** Serves as the brain of the application, utilizing Java Servlets to process user requests and manage data flow.


---

## 🛠 Tech Stack
* **Backend:** Java (Servlets & JSP)
* **Frontend:** HTML5, CSS3, JavaScript
* **Database:** MySQL
* **Server:** Apache Tomcat
* **API/Library:** JDBC (Java Database Connectivity)
* **IDE:** Eclipse / NetBeans

---

## ⚙️ Setup Instructions
1. **Database Setup:** * Import the `database.sql` file located in the `/sql` folder into your MySQL server.
   * Ensure the database name matches the configuration in your JDBC connection string.
2. **Server Configuration:** * Deploy the project on an **Apache Tomcat** server.
   * Add the MySQL Connector JAR to your project's build path.
3. **Execution:** * Access the application via `http://localhost:8080/LeaveFlow` (or your specific port).

---
