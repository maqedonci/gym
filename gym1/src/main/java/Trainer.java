import java.util.ArrayList;
import java.util.List;

public class Trainer extends User {
    // Atributet specifike për Trainer
    private String specialization;
    private List<Session> sessionSchedule;

    // Konstruktori
    public Trainer(int id, String name, String email, String password, String specialization) {
        super(id, name, email, password); // Thirr konstruktin e klasës bazë
        this.specialization = specialization; // Inicializo specialization
        this.sessionSchedule = new ArrayList<>(); // Inicializo listën e seancave
    }

    // Getters dhe Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Session> getSessionSchedule() {
        return sessionSchedule;
    }

    // Metoda për planifikimin e seancave
    public void scheduleSession(Session session) {
        sessionSchedule.add(session); // Shto seancën në listë
        System.out.println("Session scheduled by trainer " + getName() + ": " + session);
    }

    // Metoda për të parë listën e anëtarëve
    public void viewMembers(List<Member> members) {
        if (members == null || members.isEmpty()) {
            System.out.println("No members available for trainer " + getName());
            return;
        }
        System.out.println("Members handled by trainer " + getName() + ":");
        for (Member member : members) {
            System.out.println("- " + member.getName() + " (Email: " + member.getEmail() + ")");
        }
    }
}

