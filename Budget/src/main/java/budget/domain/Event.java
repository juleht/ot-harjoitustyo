package budget.domain;

import java.time.LocalDate;

public class Event {

    private Integer id;
    private double amount;
    private String eventtype;
    private LocalDate eventdate;
    private User user;

    public Event(Integer id, double amount, String eventtype, LocalDate eventdate, User user) {
        this.id = id;
        this.amount = amount;
        this.eventtype = eventtype;
        this.eventdate = eventdate;
        this.user = user;
    }

    public Event(double amount, String eventtype, LocalDate eventdate, User user) {
        this.amount = amount;
        this.eventtype = eventtype;
        this.eventdate = eventdate;
        this.user = user;
    }

    public Event(Integer id, double amount, String eventtype, LocalDate eventdate) {
        this.id = id;
        this.eventtype = eventtype;
        this.amount = amount;
        this.eventdate = eventdate;
    }

    @Override
    public String toString() {
        return this.amount + " " + this.eventtype + " " + this.eventdate;
    }

    public LocalDate getEventdate() {
        return eventdate;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setEventdate(LocalDate eventdate) {
        this.eventdate = eventdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

}
