public class MembershipService implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Payment of $" + amount + " processed successfully.");
    }
}

