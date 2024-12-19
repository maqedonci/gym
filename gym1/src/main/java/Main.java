import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of AuthService to handle user registration and login
        AuthService authService = new AuthService();

        // Create some users (Members and Trainers)
        Trainer trainer1 = new Trainer(1, "John Doe", "john@example.com", "password123", "Yoga");
        Trainer trainer2 = new Trainer(2, "Jane Smith", "jane@example.com", "password123", "Fitness");

        Member member1 = new Member(3, "Alice Brown", "alice@example.com", "password123", SubscriptionType.PREMIUM);
        Member member2 = new Member(4, "Bob Green", "bob@example.com", "password123", SubscriptionType.BASIC);

        // Register users
        authService.register(trainer1);
        authService.register(trainer2);
        authService.register(member1);
        authService.register(member2);

        // Log in a user (example: Alice)
        User loggedInUser = authService.login("alice@example.com", "password123");

        if (loggedInUser != null) {
            // If the login is successful, let's demonstrate some functionalities:
            // If the logged in user is a member, allow joining sessions
            if (loggedInUser instanceof Member) {
                Member loggedInMember = (Member) loggedInUser;

                // Create some sessions
                Session session1 = new Session("Yoga Class", "2024-12-21", "10:00 AM");
                Session session2 = new Session("Fitness Training", "2024-12-22", "11:00 AM");

                // Member joins a session
                loggedInMember.joinSession(session1);

                // Member tries to join another session (if Basic subscription, limit can be enforced)
                loggedInMember.joinSession(session2);

                // View trainer details
                loggedInMember.viewTrainerDetails(trainer1);
            }

            // If the logged in user is a trainer, allow viewing members and scheduling sessions
            if (loggedInUser instanceof Trainer) {
                Trainer loggedInTrainer = (Trainer) loggedInUser;

                // Trainer views their members
                List<Member> members = new ArrayList<>();
                members.add(member1);
                members.add(member2);
                loggedInTrainer.viewMembers(members);

                // Trainer schedules a session
                Session session3 = new Session("Advanced Yoga", "2024-12-23", "12:00 PM");
                loggedInTrainer.scheduleSession(session3);
            }
        } else {
            System.out.println("Unable to log in.");
        }
    }
}


