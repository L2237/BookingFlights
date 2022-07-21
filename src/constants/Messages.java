package constants;

import models.Flight;
import models.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Messages {
    public static String getUserAlreadyExists() {
        return "User already exists!";
    }

    public static String getUserAdded(String email) {
        return "User with email: " + email + " was successfully added!";
    }

    public static String getCannotAddUserPasswordDiff() {
        return "Cannot add user! The passwords are different!";
    }

    public static String getCannotAddUserPasswordTooShort() {
        return "Cannot add user! Password too short!";
    }

    public static String getCannotAddUserEmailUsed() {
        return "Cannot add user! Email address already used!";
    }

    public static String getCannotFindUser(String email) {
        return "Cannot find user with email: " + email;
    }

    public static String getIncorrectPassword() {
        return "Incorrect password!";
    }

    public static String getDisplayLoginUser(User user, LocalDateTime dateTime) {
        return "User with email: " + user.getEmail() + " is the current user started from " + dateTime;
    }

    public static String getAnotherUserIsConnected() {
        return "Another user is already connected!";
    }

    public static String getCannotLogoutUser(String email) {
        return "The user with email " + email + " was not connected!";
    }

    public static String getLogout(String email, LocalDateTime dateTime) {
        return "User with email " + email + "successfully disconnected at " + dateTime;
    }

    public static String getDisplayFlight(Flight flight) {
        return "Flight from " + flight.getFrom() + " to " + flight.getTo() + ", date " + flight.getDate() +
                ", duration: " + flight.getDuration();
    }

    public static String getNoConnectedUser() {
        return "There is no connected user!";
    }

    public static String getNoFlightWithId(int flightId) {
        return "The flight with id " + flightId + " does not exist!";
    }

    public static String getUserAlreadyHasTicket(String email, int flightId) {
        return "The user with email " + email + " already has a ticket for the flight with id " + flightId;
    }

    public static String getUserAddedFlight(String email, int flightId) {
        return "The flight with id " + flightId + " was successfully added for user " + email;
    }

    public static String getCannotAddFlightWitId(int flightId) {
        return "Cannot add flight! There is already a flight with id = " + flightId;
    }

    public static String getUserDoesNotHaveTicket(User user, int flightId) {
        return "The user with email " + user.getEmail() + " does not have a ticket for the flight with id " + flightId;
    }

    public static String getUserCanceledTicket(User user, int flightId) {
        return "The user with email " + user.getEmail() + " has successfully canceled his ticket for flight with id " + flightId;
    }

    public static String getAddedFlight(Flight flight) {
        return "Flight from " + flight.getFrom() + " to " + flight.getTo() + ", date " + flight.getDate() +
                ", duration: " + flight.getDuration() + " successfully added!";
    }

    public static String getFlightWithIdDeleted(int flightId) {
        return "The flight with id " + flightId + " successfully deleted!";
    }

    public static String getNotifyUserFlightWasCanceled(User user, int flightId) {
        return "The user with email " + user.getEmail() + " was notified that the flight with id " + flightId + " was canceled!";
    }

    public static String getPersistFlights(LocalTime time) {
        return "The flights were successfully saved in the database at " + time;
    }

    public static String getPersistUsers(LocalTime time) {
        return "The users were successfully saved in the database at " + time;
    }

    public static String commandNotYeImplemented() {
        return "This command is not yet implemented";
    }
}