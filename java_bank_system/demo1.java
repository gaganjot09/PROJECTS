import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

class User implements Serializable {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

class Account implements Serializable {
    private int accountNumber;
    private String accountType;
    private double balance;
    private List<String> transactionHistory;

    public Account(int accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

class Bank implements Serializable {
    private List<User> users;
    private List<Account> accounts;
    private User loggedInUser;

    public Bank() {
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.loggedInUser = null;
    }
     )

    public boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void createUser(String username, String password) {
        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please provide a different username.");
            return;
        }

        User newUser = new User(username, password, false);
        users.add(newUser);
        System.out.println("User created successfully.");
    }

    public void createAdminUserAccount(String username, String password, String accountType) {
        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please provide a different username.");
            return;
        }

        User newUser = new User(username, password, false);
        users.add(newUser);

        int accountNumber = accounts.size() + 1;
        Account newAccount = new Account(accountNumber, accountType);
        accounts.add(newAccount);
        System.out.println("User account created successfully. Account number: " + accountNumber);

        // Display the newly created user's credentials
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("User logged in successfully.");
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    public void logoutUser() {
        loggedInUser = null;
        System.out.println("User logged out successfully.");
    }

    public void createAccount(String accountType) {
        int accountNumber = accounts.size() + 1;
        Account newAccount = new Account(accountNumber, accountType);
        accounts.add(newAccount);
        System.out.println("Account created successfully. Account number: " + accountNumber);
    }
    // New method for creating a user account by the admin
    
    public Account findAccountByNumber(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    public void deposit(int accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            double currentBalance = account.getBalance();
            double updatedBalance = currentBalance + amount;
            account.setBalance(updatedBalance);
            account.getTransactionHistory().add("Deposit: +" + amount);
            System.out.println("Deposit successful. New balance: " + updatedBalance);
        }
    }

    public void withdraw(int accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            double currentBalance = account.getBalance();
            if (currentBalance >= amount) {
                double updatedBalance = currentBalance - amount;
                account.setBalance(updatedBalance);
                account.getTransactionHistory().add("Withdrawal: -" + amount);
                System.out.println("Withdrawal successful. New balance: " + updatedBalance);
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    public void transfer(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        Account sourceAccount = findAccountByNumber(sourceAccountNumber);
        Account destinationAccount = findAccountByNumber(destinationAccountNumber);

        if (sourceAccount != null && destinationAccount != null) {
            double sourceBalance = sourceAccount.getBalance();
            if (sourceBalance >= amount) {
                double destinationBalance = destinationAccount.getBalance();
                double updatedSourceBalance = sourceBalance - amount;
                double updatedDestinationBalance = destinationBalance + amount;
                sourceAccount.setBalance(updatedSourceBalance);
                destinationAccount.setBalance(updatedDestinationBalance);
                sourceAccount.getTransactionHistory().add("Transfer to " + destinationAccountNumber + ": -" + amount);
                destinationAccount.getTransactionHistory().add("Transfer from " + sourceAccountNumber + ": +" + amount);
                System.out.println("Transfer successful. New source balance: " + updatedSourceBalance);
            } else {
                System.out.println("Insufficient balance in the source account.");
            }
        }
    }

    public void displayAccountSummary(int accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Transaction History:");
            for (String transaction : account.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    public void saveData(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(users);
            outputStream.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            users = (List<User>) inputStream.readObject();
            accounts = (List<Account>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    // New method for creating a user account
    public void createUserAccount(String username, String password) {
        User newUser = new User(username, password, false);
        users.add(newUser);
        System.out.println("User account created successfully.");
    }

    // New method for blocking a user account (Admin functionality)
    public void blockUserAccount(String username) {
        User userToBlock = findUserByUsername(username);
        if (userToBlock != null && !userToBlock.isAdmin()) {
            users.remove(userToBlock);
            System.out.println("User account blocked successfully.");
        } else {
            System.out.println("User not found or cannot block admin accounts.");
        }
    }

    // Helper method to find a user by their username
    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // New method for deleting a user account (Admin functionality)
    public void deleteUserAccount(String username, int accountNumber) {
    User userToDelete = findUserByUsername(username);
    if (userToDelete != null && !userToDelete.isAdmin()) {
        // Check if the user owns the account before deleting
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                users.remove(userToDelete);
                System.out.println("User account deleted successfully.");
                return;
            }
        }
        System.out.println("User does not own the specified account.");
    } else {
        System.out.println("User not found or cannot delete admin accounts.");
    }
}

}


public class demo4 {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Bank Management System!");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.println("User Login");
                    System.out.println("Enter username:");
                    String userUsername = scanner.nextLine();
                    System.out.println("Enter password:");
                    String userPassword = scanner.nextLine();
                    User user = bank.loginUser(userUsername, userPassword);
                    if (user != null && !user.isAdmin()) {
                        boolean userLogout = false;
                        while (!userLogout) {
                            System.out.println("\nPlease select an option:");
                            // System.out.println("1. Create Account");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer");
                            System.out.println("4. Display Account Summary");
                            System.out.println("5. Logout");
                            System.out.println("0. Exit");

                            int userChoice = scanner.nextInt();
                            scanner.nextLine(); 
                            switch (userChoice) {
                                case 9:
                                    System.out.println("Create Account");
                                    System.out.println("Select account type:");
                                    System.out.println("1. Savings Account");
                                    System.out.println("2. Checking Account");
                                    int accountChoice = scanner.nextInt();
                                    scanner.nextLine(); 
                                    String accountType;
                                    if (accountChoice == 1) {
                                        accountType = "Savings Account";
                                    } else if (accountChoice == 2) {
                                        accountType = "Checking Account";
                                    } else {
                                        System.out.println("Invalid choice.");
                                        continue;
                                    }
                                    bank.createAccount(accountType);
                                    break;
                                case 1:
                                    System.out.println("Deposit");
                                    System.out.println("Enter account number:");
                                    int depositAccountNumber = scanner.nextInt();
                                    System.out.println("Enter amount to deposit:");
                                    double depositAmount = scanner.nextDouble();
                                    scanner.nextLine(); 
                                    bank.deposit(depositAccountNumber, depositAmount);
                                    break;
                                case 2:
                                    System.out.println("Withdraw");
                                    System.out.println("Enter account number:");
                                    int withdrawAccountNumber = scanner.nextInt();
                                    System.out.println("Enter amount to withdraw:");
                                    double withdrawAmount = scanner.nextDouble();
                                    scanner.nextLine(); 
                                    bank.withdraw(withdrawAccountNumber, withdrawAmount);
                                    break;
                                case 3:
                                    System.out.println("Transfer");
                                    System.out.println("Enter source account number:");
                                    int transferSourceAccountNumber = scanner.nextInt();
                                    System.out.println("Enter destination account number:");
                                    int transferDestinationAccountNumber = scanner.nextInt();
                                    System.out.println("Enter amount to transfer:");
                                    double transferAmount = scanner.nextDouble();
                                    scanner.nextLine(); 
                                    bank.transfer(transferSourceAccountNumber, transferDestinationAccountNumber, transferAmount);
                                    break;
                                case 4:
                                    System.out.println("Display Account Summary");
                                    System.out.println("Enter account number:");
                                    int accountNumber = scanner.nextInt();
                                    scanner.nextLine(); 
                                    bank.displayAccountSummary(accountNumber);
                                    break;
                                case 5:
                                    bank.logoutUser();
                                    System.out.println("User logged out successfully.");
                                    userLogout = true;
                                    break;
                                case 0:
                                    exit = true;
                                    userLogout = true;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 2:
                    System.out.println("Admin Login");
                    System.out.println("Enter username:");
                    String adminUsername = scanner.nextLine();
                    System.out.println("Enter password:");
                    String adminPassword = scanner.nextLine();
                    if (adminUsername.equals("admin") && adminPassword.equals("adminpass")) {
                        User admin = new User(adminUsername, adminPassword, true);
                        bank.loginUser(adminUsername, adminPassword);
                        if (admin != null && admin.isAdmin()) {
                            boolean adminLogout = false;
                            while (!adminLogout) {
                                System.out.println("\nPlease select an option:");
                                System.out.println("1. Create Account");
                                System.out.println("2. Deposit");
                                System.out.println("3. Withdraw");
                                System.out.println("4. Transfer");
                                System.out.println("5. Display Account Summary");
                                System.out.println("6. Logout");
                                // System.out.println("7. Block User Account");
                                System.out.println("7. Delete User Account");
                                System.out.println("0. Exit");

                                int adminChoice = scanner.nextInt();
                                scanner.nextLine(); 

                                switch (adminChoice) {
                                    case 1:
                                        System.out.println("Create Account");
                                        System.out.println("Enter username for the new user account:");
                                        String newUserUsername = scanner.nextLine();
                                        System.out.println("Enter password for the new user account:");
                                        String newUserPassword = scanner.nextLine();
                                        System.out.println("Select account type:");
                                        System.out.println("1. Savings Account");
                                        System.out.println("2. Checking Account");
                                        int accountChoice = scanner.nextInt();
                                        scanner.nextLine(); 
                                        String accountType;
                                        if (accountChoice == 1) {
                                            accountType = "Savings Account";
                                        } else if (accountChoice == 2) {
                                            accountType = "Checking Account";
                                        } else {
                                            System.out.println("Invalid choice.");
                                            continue;
                                        }
                                        bank.createAdminUserAccount(newUserUsername, newUserPassword,accountType);
                                        break;
                                    case 2:
                                        System.out.println("Deposit");
                                        System.out.println("Enter account number:");
                                        int depositAccountNumber = scanner.nextInt();
                                        System.out.println("Enter amount to deposit:");
                                        double depositAmount = scanner.nextDouble();
                                        scanner.nextLine(); 
                                        bank.deposit(depositAccountNumber, depositAmount);
                                        break;
                                    case 3:
                                        System.out.println("Withdraw");
                                        System.out.println("Enter account number:");
                                        int withdrawAccountNumber = scanner.nextInt();
                                        System.out.println("Enter amount to withdraw:");
                                        double withdrawAmount = scanner.nextDouble();
                                        scanner.nextLine(); 
                                        bank.withdraw(withdrawAccountNumber, withdrawAmount);
                                        break;
                                    case 4:
                                        System.out.println("Transfer");
                                        System.out.println("Enter source account number:");
                                        int transferSourceAccountNumber = scanner.nextInt();
                                        System.out.println("Enter destination account number:");
                                        int transferDestinationAccountNumber = scanner.nextInt();
                                        System.out.println("Enter amount to transfer:");
                                        double transferAmount = scanner.nextDouble();
                                        scanner.nextLine(); 
                                        bank.transfer(transferSourceAccountNumber, transferDestinationAccountNumber, transferAmount);
                                        break;
                                    case 5:
                                        System.out.println("Display Account Summary");
                                        System.out.println("Enter account number:");
                                        int accountNumber = scanner.nextInt();
                                        scanner.nextLine(); 
                                        bank.displayAccountSummary(accountNumber);
                                        break;
                                    case 6:
                                        bank.logoutUser();
                                        System.out.println("Admin logged out successfully.");
                                        adminLogout = true;
                                        break;
                                    case 8:
                                        System.out.println("Block User Account");
                                        System.out.println("Enter username to block:");
                                        String userToBlock = scanner.nextLine();
                                        bank.blockUserAccount(userToBlock);
                                        break;
                                    case 7:
                                        System.out.println("Delete User Account");
                                        System.out.println("Enter username to delete:");
                                        String userToDelete = scanner.nextLine();
                                        System.out.println("Enter account number to delete:");
                                        int accountNumberToDelete = scanner.nextInt();
                                        scanner.nextLine();
                                        bank.deleteUserAccount(userToDelete, accountNumberToDelete); 
                                        break;
                                    case 0:
                                        exit = true;
                                        adminLogout = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 0:
                    System.out.println("Thank you for using the Bank Management System!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
