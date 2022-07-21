package services;

import constants.Messages;
import models.Flight;
import models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AirLineManager {
    private final FileWriterService writerService = new FileWriterService();
    private List<User> users = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private User currentUser = null;

    public void signup(String[] commands) {
        // TODO check commands.length = 5
        // parsare informatii
        String email = commands[1];
        String name = commands[2];
        String password = commands[3];
        String confirmationPassword = commands[4];

        // verificari
        boolean userExists = users.stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList())
                .contains(email);
        if (userExists) {
            writerService.write(Messages.getUserAlreadyExists());
            return;
        }
        if (!password.equals(confirmationPassword)) {
            writerService.write(Messages.getCannotAddUserPasswordDiff());
            return;
        }
        if (password.length() < 8) {
            writerService.write(Messages.getCannotAddUserPasswordTooShort());
            return;
        }
        // totul e corect
        User user = new User(email, name, password);
        users.add(user);
        writerService.write(Messages.getUserAdded(email));
    }

    public void login(String[] commands) {
        String email = commands[1];
        String password = commands[2];

        boolean userExists = users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
        if (!userExists) {
            writerService.write(Messages.getCannotFindUser(email));
            return;
        }

        userExists = users.stream()
                .anyMatch(user -> user.getPassword().equals(password));
        if (!userExists) {
            writerService.write(Messages.getIncorrectPassword());
            return;
        }
        if (currentUser != null) {
            writerService.write(Messages.getAnotherUserIsConnected());
            return;
        }
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            writerService.write(Messages.getDisplayLoginUser(currentUser, LocalDateTime.now()));
        }
    }

    public void logout(String[] commands) {
        String email = commands[1];

        if (currentUser == null || !currentUser.getEmail().equals(email)) {
            writerService.write(Messages.getAnotherUserIsConnected());
            return;
        }
        currentUser = null;
        writerService.write(Messages.getLogout(email, LocalDateTime.now()));
    }

    public void displayMyFlights(String[] commands) {
        System.out.println("Vrei sa faci DISPLAY_MY_FLIGHTS");
    }

    public void addFlight(String[] commands) {
        System.out.println("Vrei sa faci ADD_FLIGHT");
    }

    public void cancelFlight(String[] commands) {
        System.out.println("Vrei sa faci CANCEL_FLIGHT");
    }

    public void addFlightDetails(String[] commands) {
        System.out.println("Vrei sa faci ADD_FLIGHT_DETAILS");
    }

    public void deleteFlight(String[] commands) {
        System.out.println("Vrei sa faci DELETE_FLIGHT");
    }

    public void displayFlights(String[] commands) {
        System.out.println("Vrei sa faci DISPLAY_FLIGHTS");
    }

    public void persistFlights(String[] commands) {
        System.out.println("Vrei sa faci PERSIST_FLIGHTS");
    }

    public void persistUsers(String[] commands) {
        System.out.println("Vrei sa faci PERSIST_USERS");
    }

    public void invalidCommand(String[] commands) {
        System.out.println("Comanda nu este valida");
    }

    public void flush() {
        writerService.flush();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<User> getUsers() {
        return users;
    }
}
