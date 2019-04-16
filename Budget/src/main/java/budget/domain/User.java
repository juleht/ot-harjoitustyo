package budget.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Integer id;
    private String username;
    private List<Event> events;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.events = new ArrayList<>();
    }

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.events = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
