public class Session {
    private String title;
    private String date;
    private String time;

    // Konstruktori
    public Session(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    // Getters dhe Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Për të shfaqur detajet e seancës
    @Override
    public String toString() {
        return "Session: " + title + ", Date: " + date + ", Time: " + time;
    }
}
