import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserAuth {
    private static final String USER_FILE = System.getProperty("user.dir") + File.separator + "users.txt";

    public static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("User File Not Found Or Unreadable.");
        }
        return users;
    }

    public static boolean authenticate(String email, String password) {
        Map<String, String> users = loadUsers();
        return users.containsKey(email) && users.get(email).equals(password);
    }
}

