import java.util.*;
import java.io.*;

// ================= MAIN CLASS =================
public class MainSystem {
    static Scanner sc = new Scanner(System.in);

    // Storage
    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Attendance> attendanceList = new ArrayList<>();
    static ArrayList<Leave> leaves = new ArrayList<>();
    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        login();

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. HRMS");
            System.out.println("2. Expense Tracker");
            System.out.println("3. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> hrmsMenu();
                case 2 -> expenseMenu();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= LOGIN =================
    static void login() {
        System.out.println("===== LOGIN =====");
        String user = "admin";
        String pass = "1234";

        while (true) {
            System.out.print("Username: ");
            String u = sc.next();
            System.out.print("Password: ");
            String p = sc.next();

            if (u.equals(user) && p.equals(pass)) {
                System.out.println("Login Successful!");
                break;
            } else {
                System.out.println("Invalid credentials!");
            }
        }
    }

    // ================= HRMS =================
    static void hrmsMenu() {
        while (true) {
            System.out.println("\n--- HRMS MENU ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Mark Attendance");
            System.out.println("5. View Attendance");
            System.out.println("6. Leave Management");
            System.out.println("7. Search Employee");
            System.out.println("8. Back");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> deleteEmployee();
                case 4 -> markAttendance();
                case 5 -> viewAttendance();
                case 6 -> leaveMenu();
                case 7 -> searchEmployee();
                case 8 -> { return; }
                default -> System.out.println("Invalid!");
            }
        }
    }

    static void addEmployee() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Dept: ");
        String dept = sc.nextLine();

        employees.add(new Employee(id, name, dept));
        System.out.println("Employee Added!");
    }

    static void viewEmployees() {
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    static void deleteEmployee() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        employees.removeIf(e -> e.id == id);
        System.out.println("Deleted!");
    }

    static void searchEmployee() {
        System.out.print("Enter Name: ");
        String name = sc.next();

        for (Employee e : employees) {
            if (e.name.equalsIgnoreCase(name)) {
                System.out.println(e);
            }
        }
    }

    // ================= ATTENDANCE =================
    static void markAttendance() {
        System.out.print("Emp ID: ");
        int id = sc.nextInt();
        System.out.print("Status (Present/Absent/Leave): ");
        String status = sc.next();

        attendanceList.add(new Attendance(id, status));
        System.out.println("Attendance Marked!");
    }

    static void viewAttendance() {
        for (Attendance a : attendanceList) {
            System.out.println(a);
        }
    }

    // ================= LEAVE =================
    static void leaveMenu() {
        System.out.println("1. Apply Leave");
        System.out.println("2. View Leaves");
        int ch = sc.nextInt();

        if (ch == 1) {
            System.out.print("Emp ID: ");
            int id = sc.nextInt();
            System.out.print("Type: ");
            String type = sc.next();

            leaves.add(new Leave(id, type, "Pending"));
            System.out.println("Leave Requested!");
        } else {
            for (Leave l : leaves) {
                System.out.println(l);
            }
        }
    }

    // ================= EXPENSE =================
    static void expenseMenu() {
        while (true) {
            System.out.println("\n--- EXPENSE MENU ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Total Expense");
            System.out.println("5. Back");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> deleteExpense();
                case 4 -> totalExpense();
                case 5 -> { return; }
            }
        }
    }

    static void addExpense() {
        System.out.print("Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();

        expenses.add(new Expense(amt, cat));
        System.out.println("Expense Added!");
    }

    static void viewExpenses() {
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    static void deleteExpense() {
        System.out.print("Enter index: ");
        int i = sc.nextInt();
        expenses.remove(i);
        System.out.println("Deleted!");
    }

    static void totalExpense() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("Total = " + total);
    }
}

// ================= CLASSES =================

class Employee {
    int id;
    String name, dept;

    Employee(int id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    public String toString() {
        return id + " | " + name + " | " + dept;
    }
}

class Attendance {
    int id;
    String status;

    Attendance(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public String toString() {
        return id + " -> " + status;
    }
}

class Leave {
    int id;
    String type, status;

    Leave(int id, String type, String status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public String toString() {
        return id + " | " + type + " | " + status;
    }
}

class Expense {
    double amount;
    String category;

    Expense(double amount, String category) {
        this.amount = amount;
        this.category = category;
    }

    public String toString() {
        return amount + " | " + category;
    }
}
