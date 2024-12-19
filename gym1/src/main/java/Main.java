import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Krijojmë disa koleksione për përdoruesit, anëtarët dhe trajnerët
        List<User> users = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        List<Trainer> trainers = new ArrayList<>();
        MembershipService membershipService = new MembershipService();

        Scanner scanner = new Scanner(System.in);

        // Pjesa ku përdoruesi mund të regjistrohet
        System.out.println("Welcome to the Gym Management System!");
        System.out.println("Please register to continue.");

        // Kërkojmë të dhënat për regjistrimin e përdoruesit
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Krijojmë një objekt për përdoruesin (këtu mund të shtoni logjikën për të zgjedhur mes anëtarëve dhe trajnerëve)
        System.out.print("Are you a member or trainer? (Enter 'member' or 'trainer'): ");
        String role = scanner.nextLine().toLowerCase();

        System.out.println("\nChoose your Subscription Type:");
        System.out.println("1. BASIC - Access to all gym equipment (20 EUR/month)");
        System.out.println("2. PREMIUM - Personal trainer support (40 EUR/month)");
        System.out.println("3. VIP - Personal trainer and nutritionist support (120 EUR/month)");
        System.out.print("Enter your subscription type (1, 2, or 3): ");
        int subscriptionChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        SubscriptionType subscriptionType = null;
        switch (subscriptionChoice) {
            case 1:
                subscriptionType = SubscriptionType.BASIC;
                break;
            case 2:
                subscriptionType = SubscriptionType.PREMIUM;
                break;
            case 3:
                subscriptionType = SubscriptionType.VIP;
                break;
            default:
                System.out.println("Invalid choice! Setting default subscription to BASIC.");
                subscriptionType = SubscriptionType.BASIC;
                break;
        }

        // Insert user data into the database
        try (Connection connection = DatabaseUtil.getConnection()) {
            String insertQuery = "INSERT INTO users (name, email, password, role, subscription_type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, role);
                stmt.setString(5, subscriptionType.name());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User registered successfully: " + name);
                } else {
                    System.out.println("Error during registration.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pjesa ku përdoruesi mund të bëjë login
        System.out.println("\nPlease log in to continue.");
        System.out.print("Enter your email: ");
        String loginEmail = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        // Check user in the database
        User loggedInUser = null;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String loginQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(loginQuery)) {
                stmt.setString(1, loginEmail);
                stmt.setString(2, loginPassword);

                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    String nameFromDb = resultSet.getString("name");
                    String roleFromDb = resultSet.getString("role");
                    String subscriptionTypeFromDb = resultSet.getString("subscription_type");

                    // Based on role, create user object
                    if (roleFromDb.equals("member")) {
                        loggedInUser = new Member(resultSet.getInt("id"), nameFromDb, loginEmail, loginPassword, SubscriptionType.valueOf(subscriptionTypeFromDb));
                    } else if (roleFromDb.equals("trainer")) {
                        loggedInUser = new Trainer(resultSet.getInt("id"), nameFromDb, loginEmail, loginPassword, "Specialization");
                    }

                    System.out.println("\nLogin successful!");
                    System.out.println("Name: " + nameFromDb);
                    System.out.println("Role: " + roleFromDb);
                    System.out.println("Subscription Type: " + subscriptionTypeFromDb);
                } else {
                    System.out.println("Login failed! Please check your credentials.");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pas login-it, përdoruesi mund të zgjedhë opsionet
        if (loggedInUser instanceof Member) {
            Member member = (Member) loggedInUser;
            System.out.println("\nHello " + member.getName() + "!");
            System.out.println("Subscription Type: " + member.getSubscriptionType());

            // Opsionet për anëtarin
            System.out.println("1. Join a Session");
            System.out.println("2. View Trainer Details");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                // Join a session
                System.out.print("Enter session title: ");
                String title = scanner.nextLine();
                System.out.print("Enter session date: ");
                String date = scanner.nextLine();
                System.out.print("Enter session time: ");
                String time = scanner.nextLine();

                Session session = new Session(title, date, time);
                member.joinSession(session);
            } else if (choice == 2) {
                // View trainer details
                System.out.println("Trainer Details:");
                // Make sure to use the correct trainer object that the member is associated with
                if (!trainers.isEmpty()) {
                    Trainer trainer = trainers.get(0); // Assuming the first trainer for demonstration
                    trainer.viewMembers(members); // Trainer can view members
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (loggedInUser instanceof Trainer) {
            Trainer trainer = (Trainer) loggedInUser;
            System.out.println("\nHello Trainer " + trainer.getName() + "!");

            // Opsionet për trajnerin
            System.out.println("1. Schedule a Session");
            System.out.println("2. View Members");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                // Schedule a session
                System.out.print("Enter session title: ");
                String title = scanner.nextLine();
                System.out.print("Enter session date: ");
                String date = scanner.nextLine();
                System.out.print("Enter session time: ");
                String time = scanner.nextLine();

                Session session = new Session(title, date, time);
                trainer.scheduleSession(session);
            } else if (choice == 2) {
                // View members
                trainer.viewMembers(members);  // Trainer can view members
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}






