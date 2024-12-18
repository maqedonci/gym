import java.util.ArrayList;
import java.util.List;

public abstract class User {
    // Atributet
    private int id;
    private String name;
    private String email;
    private String password;
    private List<String> posts;
    private List<String> purchaseHistory;

    // Konstruktori
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.posts = new ArrayList<>();
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters dhe Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPosts() {
        return posts;
    }

    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    // Metodat
    public void register() {
        System.out.println("User " + name + " has been registered successfully!");
    }

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println("Login successful for user: " + name);
            return true;
        } else {
            System.out.println("Invalid email or password.");
            return false;
        }
    }

    public void logout() {
        System.out.println("User " + name + " has logged out.");
    }

    public void addPost(String post) {
        posts.add(post);
        System.out.println("Post added by " + name + ": " + post);
    }

    public void viewPurchaseHistory() {
        System.out.println("Purchase history of " + name + ":");
        for (String purchase : purchaseHistory) {
            System.out.println("- " + purchase);
        }
    }
}
