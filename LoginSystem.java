import java.io.*;
import java.util.*;
import java.util.regex.*;

public class LoginSystem {

    private static final String USER_FILE = System.getProperty("user.dir") + File.separator + "users.txt";

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private static void registerUser(Scanner scanner) {
        try {
            System.out.print("Enter Your Email: ");
            String email = scanner.nextLine();

            if (!isValidEmail(email)) {
                System.out.println("Invalid Email Format. Please Try Again!");
                return;
            }

            System.out.print("Enter Your Password: ");
            String password = scanner.nextLine();

            if (!isValidPassword(password)) {
                System.out.println("Password Must Be at Least 6 Characters!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(email)) {
                    System.out.println("User Already Exists! Please Log In Instead.");
                    reader.close();
                    return;
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true));
            writer.write(email + "," + password);
            writer.newLine();
            writer.close();

            System.out.println("Registration Successful!");
        } catch (IOException e) {
            System.out.println("Error Saving User Data!");
        }
    }

    private static boolean loginUser(Scanner scanner) {
        try {
            System.out.print("Enter Your Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Your Password: ");
            String password = scanner.nextLine();

            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(email) && parts[1].equals(password)) {
                    System.out.println("Login Successful! Welcome Back, " + email + "!");
                    reader.close();
                    return true;
                }
            }
            reader.close();
            System.out.println("Invalid Email Or Password!");
        } catch (IOException e) {
            System.out.println("Error Reading User Data!");
        }
        return false;
    }

    public static boolean startLoginSystem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome To The Medicine Inventory System ===");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.print("Choose An Option: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            return loginUser(scanner);
        } else if (choice.equals("2")) {
            registerUser(scanner);
            return startLoginSystem();
        } else {
            System.out.println("Invalid Choice!");
            return startLoginSystem();
        }
    }
}
