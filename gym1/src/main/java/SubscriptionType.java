public enum SubscriptionType {
    BASIC("Access to all gym equipment", 20),
    PREMIUM("Personal trainer care", 80),
    VIP(" Personal trainer and meal plan for everyday of the week (supplements included) ", 180);

    private final String description;
    private final double price;

    // Konstruktori
    SubscriptionType(String description, double price) {
        this.description = description;
        this.price = price;
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}

