import java.io.*;
import java.util.*;

public class UserHandler {

    private static final String USER_FILE = System.getProperty("user.dir") + File.separator + "users.txt";

    public static boolean validateUser(String email, String password) {
        Map<String, String> users = loadUsers();
        return users.containsKey(email) && users.get(email).equals(password);
    }

    public static boolean checkUserExists(String email) {
        Map<String, String> users = loadUsers();
        return users.containsKey(email);
    }

    public static void updatePassword(String email, String newPassword) {
        Map<String, String> users = loadUsers();
        if(users.containsKey(email)){
            users.put(email, newPassword);
            saveUsers(users);
        }
    }

    private static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        File file = new File(USER_FILE);

        if(!file.exists()) {
            try { file.createNewFile(); } catch(IOException e){ e.printStackTrace(); }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length == 2){
                    users.put(parts[0], parts[1]);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return users;
    }

    private static void saveUsers(Map<String, String> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for(Map.Entry<String, String> entry : users.entrySet()){
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void addUser(String email, String password) {
        Map<String, String> users = loadUsers();
        users.put(email, password);
        saveUsers(users);
    }
}

