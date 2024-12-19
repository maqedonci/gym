import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    // Atributet specifike për Member
    private SubscriptionType subscriptionType;
    private List<Session> activeSessions;

    // Konstruktori
    public Member(int id, String name, String email, String password, SubscriptionType subscriptionType) {
        super(id, name, email, password);
        this.subscriptionType = subscriptionType;
        this.activeSessions = new ArrayList<>();
    }


    // Getters dhe Setters
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public List<Session> getActiveSessions() {
        return activeSessions;
    }

    // Metodat
    public void joinSession(Session session) {
        activeSessions.add(session);
        System.out.println(getName() + " has joined the session: " + session);
    }

    public void viewTrainerDetails(Trainer trainer) {
        System.out.println("Trainer details:");
        System.out.println("Name: " + trainer.getName());
        System.out.println("Specialization: " + trainer.getSpecialization());
    }
}

