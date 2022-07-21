package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String email;
    private final String name;
    private final String password;
    private final List<Flight> flights;
    private static int count;

    public User(String email, String name, String password) {
        this.id = count;
        this.email = email;
        this.name = name;
        this.password = password;
        this.flights = new ArrayList<>();
        count++;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", flights=" + flights +
                '}';
    }
}