package models;

import java.time.LocalDate;

public class Flight {
    private final int id;
    private final String from;
    private final String to;
    private final LocalDate date;
    private final int duration;

    public Flight(int id, String from, String to, LocalDate date, int duration) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}