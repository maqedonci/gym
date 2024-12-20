import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        List<Trainer> trainers = new ArrayList<>();
        MembershipService membershipService = new MembershipService();

        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to Primal Power Gym!");
        System.out.println("Please register to continue.");

        // User registration
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Are you a member or trainer? (Enter 'member' or 'trainer'): ");
        String role = scanner.nextLine().toLowerCase();

        if (role.equals("member")) {
            // Member registration
            System.out.println("\nChoose your Subscription Type:");
            System.out.println("1. BASIC - Access to all gym equipment (20 EUR/month)");
            System.out.println("2. PREMIUM - Personal trainer support (40 EUR/month)");
            System.out.println("3. VIP - Personal trainer and meal plan (180 EUR/month)");
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

            // Insert member into database
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
                        System.out.println("Member registered successfully: " + name);
                    } else {
                        System.out.println("Error during registration.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (role.equals("trainer")) {
            // Trainer registration
            System.out.println("\nChoose your area of expertise:");
            System.out.println("1. Weight Loss");
            System.out.println("2. Build Mass");
            System.out.println("3. Competition Prep");
            System.out.println("4. Athlete Training");
            System.out.println("5. General Health");
            System.out.print("Enter your expertise (1, 2, 3, 4, or 5): ");
            int expertiseChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            String expertise = null;
            switch (expertiseChoice) {
                case 1:
                    expertise = "Weight Loss";
                    break;
                case 2:
                    expertise = "Build Mass";
                    break;
                case 3:
                    expertise = "Competition Prep";
                    break;
                case 4:
                    expertise = "Athlete Training";
                    break;
                case 5:
                    expertise = "General Health";
                    break;
                default:
                    System.out.println("Invalid choice! Setting default expertise to 'General Health'.");
                    expertise = "General Health";
                    break;
            }

            // Insert trainer into database
            try (Connection connection = DatabaseUtil.getConnection()) {
                String insertQuery = "INSERT INTO users (name, email, password, role, expertise) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setString(3, password);
                    stmt.setString(4, role);
                    stmt.setString(5, expertise);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Trainer registered successfully: " + name);
                    } else {
                        System.out.println("Error during registration.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid role! Please enter 'member' or 'trainer'.");
            return;
        }

        // Login section
        System.out.println("\nPlease log in to continue.");
        System.out.print("Enter your email: ");
        String loginEmail = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

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
                    String expertiseFromDb = resultSet.getString("expertise");

                    // Create user object based on role
                    if (roleFromDb.equals("member")) {
                        loggedInUser = new Member(resultSet.getInt("id"), nameFromDb, loginEmail, loginPassword, SubscriptionType.valueOf(subscriptionTypeFromDb));
                    } else if (roleFromDb.equals("trainer")) {
                        loggedInUser = new Trainer(resultSet.getInt("id"), nameFromDb, loginEmail, loginPassword, expertiseFromDb);
                    }

                    System.out.println("\nLogin successful!");
                    System.out.println("Name: " + nameFromDb);
                    System.out.println("Role: " + roleFromDb);
                    if (roleFromDb.equals("member")) {
                        System.out.println("Subscription Type: " + subscriptionTypeFromDb);
                    } else {
                        System.out.println("Expertise: " + expertiseFromDb);
                    }
                } else {
                    System.out.println("Login failed! Please check your credentials.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Post-login options
        if (loggedInUser instanceof Member) {
            Member member = (Member) loggedInUser;
            System.out.println("\nHello " + member.getName() + "!");
            System.out.println("Subscription Type: " + member.getSubscriptionType());

            System.out.println("1. Join a Session");
            System.out.println("2. View Trainer Details");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter session title: ");
                String title = scanner.nextLine();
                System.out.print("Enter session date: ");
                String date = scanner.nextLine();
                System.out.print("Enter session time: ");
                String time = scanner.nextLine();

                Session session = new Session(title, date, time);
                member.joinSession(session);
            } else if (choice == 2) {
                System.out.println("Trainer Details:");
                if (!trainers.isEmpty()) {
                    Trainer trainer = trainers.get(0);
                    trainer.viewMembers(members);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (loggedInUser instanceof Trainer) {
            Trainer trainer = (Trainer) loggedInUser;
            System.out.println("\nHello Trainer " + trainer.getName() + "!");
            System.out.println("Expertise: " + trainer.getSpecialization());

            System.out.println("1. Schedule a Session");
            System.out.println("2. View Members");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter session title: ");
                String title = scanner.nextLine();
                System.out.print("Enter session date: ");
                String date = scanner.nextLine();
                System.out.print("Enter session time: ");
                String time = scanner.nextLine();

                Session session = new Session(title, date, time);
                trainer.scheduleSession(session);
            } else if (choice == 2) {
                trainer.viewMembers(members);
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}

