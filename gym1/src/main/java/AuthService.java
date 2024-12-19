import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users = new ArrayList<>();

    // Regjistro një përdorues të ri
    public void register(User user) {
        for (User existingUser : users) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                System.out.println("User already exists: " + user.getEmail());
                return;
            }
        }
        users.add(user);
        System.out.println("User registered: " + user.getName());
    }

    // Login për përdoruesit
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Login successful: " + user.getName());
                return user;
            }
        }
        System.out.println("Login failed!");
        return null;
    }
}

