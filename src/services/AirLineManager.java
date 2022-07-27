package services;

import constants.Messages;
import models.Flight;
import models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AirLineManager {
    private final FileWriterService writerService = new FileWriterService();
    private List<User> users = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>(Arrays.asList(
            new Flight(1, "Bucharest", "Milano", LocalDate.parse("2022-07-11"), 2),
            new Flight(2, "Bucharest", "Paris", LocalDate.parse("2022-07-11"), 2),
            new Flight(3, "Bucharest", "London", LocalDate.parse("2022-07-11"), 3)));

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
            writerService.write("");
            return;
        }
        // totul e corect
        User user = new User(email, name, password);
        users.add(user);
        writerService.write(Messages.getUserAdded(email));
        writerService.write("");
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
            writerService.write("");
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
        if(currentUser == null) {
            writerService.write(Messages.getNoConnectedUser());
        } else {
            writerService.write("");
            writerService.write("Find all the available flights for " + currentUser.getName() + " bellow: ");
            // da-mi lista de zbori pt user-ul current, parcurg lista de zboruri cu foreach si printez fiecare zbor cu: Messages.getDisplayFlight(flight)
            // metoda writerService.write -> scrie in fisieru-l output.txt
            currentUser.getFlights().forEach(flight -> writerService.write("    " + Messages.getDisplayFlight(flight)));
            writerService.write("");
        }
    }

    public void addFlight(String[] commandParams) {
        if(currentUser == null) {
            writerService.write(Messages.getNoConnectedUser());
        } else {
            // ADD_FLIGHT 1 Bucharest Milano 2022-07-11 2
            final String flightId = commandParams[1];
            final String from = commandParams[2];
            final String to = commandParams[3];
            final String date = commandParams[4];
            final String duration = commandParams[5];

            final Flight newFlight = new Flight(Integer.parseInt(flightId), from, to, LocalDate.parse(date), Integer.parseInt(duration));

            // presupunem ca zborul nu exista
            boolean flightExists = false;
            for(Flight flight: flights) {
                if(flight.getId()  == newFlight.getId()) {
                    flightExists = true; // daca exista il setam pe true
                }
            }

            // daca nu exista
            if (!flightExists) {
                writerService.write(Messages.getNoFlightWithId(newFlight.getId()));
            }

            // verificam daca userul are deja un bilet similar cumparat
            boolean alreadyHasThisTicket = false;
            if(currentUser.getFlights().size() != 0) {
                for(Flight flight: currentUser.getFlights()) {
                    if(flight.getId() == newFlight.getId()) {
                        alreadyHasThisTicket = true;
                    }
                }
            }

            if (alreadyHasThisTicket) {
                writerService.write(Messages.getUserAlreadyHasTicket(currentUser.getEmail(), newFlight.getId()));
            }

            if(flightExists && !alreadyHasThisTicket) {
                currentUser.addFlight(newFlight);
                writerService.write(Messages.getAddedFlight(newFlight));
            }
        }
    }

    public void cancelFlight(String[] commandParams) {
        if (currentUser == null) {
            writerService.write(Messages.getNoConnectedUser());
        } else {
            // obtinem zborul pe care vrem sa il anulam
            final int flightId = Integer.parseInt(commandParams[1]);

            // Verificam daca exista in lista de zboruri: FLIGHTS
            boolean flightExists = false;
            for (Flight flight : flights) {
                if (flight.getId() == flightId) {
                    flightExists = true;
                }
            }
            if (!flightExists) { // daca nu exista printam: The flight with id " + flightId + " does not exist!
                writerService.write(Messages.getNoFlightWithId(flightId));
            }
            //

            // Verificam daca exista in lista de zboruri pentru utilizator
            boolean alreadyHasTicket = false;
            if (currentUser.getFlights().size() != 0) {
                for (Flight flight : currentUser.getFlights()) {
                    if (flight.getId() == flightId) {
                        alreadyHasTicket = true;
                    }
                }
            }
            if (!alreadyHasTicket) { // Daca nu exista printam: The user with email " + user.getEmail() + " does not have a ticket for the flight with id " + flightId
                writerService.write(Messages.getUserDoesNotHaveTicket(currentUser, flightId));
            }
            //

            //  Obtin zborul din lista de zboruri pentru user-ul curent
            final Flight flightToDelete = currentUser.getFlights().stream()
                    .filter(flight -> flight.getId() == flightId)
                    .findFirst().orElse(null);

            //  daca zborul exista in lista de zboruri & are bilet
            if (flightExists && alreadyHasTicket) {
                currentUser.getFlights().remove(flightToDelete); // il stergem
                writerService.write(Messages.getNotifyUserFlightWasCanceled(currentUser, flightId)); // printam mesajul: "The user with email " + user.getEmail() + " was notified that the flight with id " + flightId + " was canceled!"
            }
            //
        }
    }

    public void addFlightDetails(String[] commandParams) {
        final int flightId = Integer.parseInt(commandParams[1]);
        final String from = commandParams[2];
        final String to = commandParams[3];
        final LocalDate date = LocalDate.parse(commandParams[4]);
        final int duration = Integer.parseInt(commandParams[5]);

        boolean flightAlreadyExists = flights.stream().anyMatch(flight -> flight.getId() == flightId);
        if(flightAlreadyExists) {
            writerService.write(Messages.getCannotAddFlightWitId(flightId));
            writerService.write(""); // printeaza o linie noua GOALA in fisierul output.txt
        } else {
            Flight newFlight = new Flight(flightId, from, to, date, duration);
            flights.add(newFlight);
            writerService.write(Messages.getAddedFlight(newFlight));
            writerService.write("");
        }
    }

    public void deleteFlight(String[] commandParams) {
        // obtinem zborul pe care vrem sa il anulam
        final int flightId = Integer.parseInt(commandParams[1]);

        // Verificam daca exista in lista de zboruri: FLIGHTS
        boolean flightExists = false;
        for (Flight flight : flights) {
            if (flight.getId() == flightId) {
                flightExists = true;
            }
        }
        if (!flightExists) { // daca nu exista printam: The flight with id " + flightId + " does not exist!
            writerService.write(Messages.getNoFlightWithId(flightId));
        }
        //


        //  Obtin zborul din lista de zboruri
        final Flight flightToDelete = flights.stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst().orElse(null);

        //  daca zborul exista in lista de zboruri & are bilet
        if (flightExists) {
            flights.remove(flightToDelete); // il stergem
            writerService.write(Messages.getFlightWithIdDeleted(flightId)); // printam mesajul: "The flight with id " + flightId + " successfully deleted!"
            writerService.write("");
            //
        }}

    public void displayFlights(String[] commandParam) {
        writerService.write("Display all the available flights: ");
        flights.forEach(flight -> writerService.write("    " + Messages.getDisplayFlight(flight)));
        writerService.write("");
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
