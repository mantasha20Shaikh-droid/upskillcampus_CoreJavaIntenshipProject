import java.util.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Address: ");
                String address = sc.nextLine();
                System.out.print("Phone: ");
                String phone = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("Initial Deposit: ");
                double deposit = sc.nextDouble();

                bank.registerUser(name, address, phone, password, deposit);
            }

            else if (choice == 2) {
                System.out.print("Account No: ");
                int accNo = sc.nextInt();
                sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();

                if (bank.login(accNo, pass)) {
                    System.out.println("Login Successful!");

                    while (true) {
                        System.out.println("\n1. View Account");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Transfer");
                        System.out.println("5. Statement");
                        System.out.println("6. Logout");

                        int opt = sc.nextInt();

                        if (opt == 1) {
                            bank.showDetails(accNo);
                        } else if (opt == 2) {
                            System.out.print("Amount: ");
                            bank.deposit(accNo, sc.nextDouble());
                        } else if (opt == 3) {
                            System.out.print("Amount: ");
                            bank.withdraw(accNo, sc.nextDouble());
                        } else if (opt == 4) {
                            System.out.print("To Account: ");
                            int to = sc.nextInt();
                            System.out.print("Amount: ");
                            double amt = sc.nextDouble();
                            bank.transfer(accNo, to, amt);
                        } else if (opt == 5) {
                            bank.showStatement(accNo);
                        } else if (opt == 6) {
                            System.out.println("Logged out successfully!");
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid Login!");
                }
            }

            else {
                System.out.println("Thank you!");
                break;
            }
        }
    }
}


// ================= BANK =================
class Bank {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Account> accounts = new HashMap<>();
    private int accountCounter = 1001;

    public int registerUser(String name, String address, String phone, String password, double deposit) {
        int accNo = accountCounter++;
        User user = new User(name, address, phone, password, accNo);
        Account acc = new Account(deposit);

        acc.addTransaction("Initial Deposit", deposit);

        users.put(accNo, user);
        accounts.put(accNo, acc);

        System.out.println("Registration Successful! Your Account No: " + accNo);
        return accNo;
    }

    public boolean login(int accNo, String password) {
        return users.containsKey(accNo) &&
               users.get(accNo).getPassword().equals(password);
    }

    public void deposit(int accNo, double amount) {
        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }
        accounts.get(accNo).deposit(amount);
    }

    public void withdraw(int accNo, double amount) {
        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }
        accounts.get(accNo).withdraw(amount);
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        if (!accounts.containsKey(fromAcc) || !accounts.containsKey(toAcc)) {
            System.out.println("Invalid account number!");
            return;
        }

        Account sender = accounts.get(fromAcc);
        Account receiver = accounts.get(toAcc);

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (sender.getBalance() < amount) {
            System.out.println("Insufficient balance!");
            return;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        sender.addTransaction("Transfer Sent", amount);
        receiver.addTransaction("Transfer Received", amount);

        System.out.println("Transfer Successful!");
    }

    public void showDetails(int accNo) {
        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }

        users.get(accNo).displayUser();
        System.out.println("Balance: " + accounts.get(accNo).getBalance());
    }

    public void showStatement(int accNo) {
        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }
        accounts.get(accNo).showTransactions();
    }
}


// ================= USER =================
class User {
    private String name;
    private String address;
    private String phone;
    private String password;
    private int accountNumber;

    public User(String name, String address, String phone, String password, int accountNumber) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void displayUser() {
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Account No: " + accountNumber);
    }
}


// ================= ACCOUNT =================
class Account {
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(double initialDeposit) {
        this.balance = initialDeposit;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
        System.out.println("Deposit Successful!");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount, balance));
            System.out.println("Withdrawal Successful!");
        }
    }

    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount, balance));
    }

    public void showTransactions() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            t.display();
        }
    }
}


// ================= TRANSACTION =================
class Transaction {
    private String type;
    private double amount;
    private double balance;
    private LocalDateTime date;

    public Transaction(String type, double amount, double balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = LocalDateTime.now();
    }

    public void display() {
        System.out.println(date + " | " + type +
                " | Amount: " + amount +
                " | Balance: " + balance);
    }
}
