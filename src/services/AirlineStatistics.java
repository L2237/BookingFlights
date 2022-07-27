package services;

import models.Flight;
import models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AirlineStatistics {
    public static String findMostUsedCityAsDepartureForFlights(AirLineManager airLineManager) {
        List<Flight> flights = airLineManager.getFlights();
        System.out.println(flights);
        System.out.println();

        // denumire pentru map: keyToValue sau valueByKey
        try {
            String city = flights.stream()
                    .collect(Collectors.groupingBy(Flight::getFrom, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max((entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue()))
                    .get()
                    .getKey();

            System.out.println("Most used city as departure flight is: ");
            System.out.println("    " + city);

            return city;
        } catch (NoSuchElementException e) {
            return null;
        }

        /*Map<String, List<Flight>> cityToFlights = flights.stream()
                .collect(Collectors.groupingBy(flight -> flight.getFrom()));

        int max = -1;
        String mostUsedCityAsDeparture = "";
        for (Map.Entry<String, List<Flight>> entry : cityToFlights.entrySet()) {
            String city = entry.getKey();
            List<Flight> cityFlights = entry.getValue();

            if (max < cityFlights.size()) {
                mostUsedCityAsDeparture = city;
                max = cityFlights.size();
            }
        }

        return mostUsedCityAsDeparture;*/
    }

    private static User findUserWhoTravelTheMost(AirLineManager manager) {
        return null;
    }

    private static List<User> findAllUsersWhoTraveledToCity(AirLineManager manager, String city) {
        return null;
    }

    private static List<Flight> findAllFlightsBetweenDates(AirLineManager manager, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public static Flight findShortestFlight(AirLineManager manager) {
        List<Flight> flights = manager.getFlights();
        Flight shortestFlight = flights.get(1);

        for(Flight flight: flights) {
            if(flight.getDuration() < shortestFlight.getDuration()) {
                shortestFlight = flight;
            }
        }

        System.out.println("Shortest flight is: ");
        System.out.println("    " + shortestFlight);

        return shortestFlight;
    }

    private static List<User> findAllUsersWhoTraveledIn(AirLineManager manager, LocalDate date) {
        return manager.getUsers()
                .stream()
                .filter(user -> user.getFlights()
                        .stream()
                        .anyMatch(flight -> flight.getDate().equals(date))
                )
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        AirLineManager airLineManager = generateAirlineManager();

        String city = findMostUsedCityAsDepartureForFlights(airLineManager);
        System.out.println(city);

        List<User> users = findAllUsersWhoTraveledIn(airLineManager, LocalDate.of(2022, 7, 13));
        System.out.println(users);
    }

    private static AirLineManager generateAirlineManager() {
        AirLineManager airLineManager = new AirLineManager();

        Flight flight1 = new Flight(1, "Sibiu", "Milano", LocalDate.of(2022, 7, 13), 60);
        Flight flight2 = new Flight(2, "Bucuresti", "Milano", LocalDate.of(2022, 7, 14), 60);
        Flight flight3 = new Flight(3, "Sibiu", "Barcelona", LocalDate.of(2022, 8, 13), 60);
        Flight flight4 = new Flight(4, "Cluj", "Milano", LocalDate.of(2022, 7, 13), 60);

        List<Flight> flights = List.of(flight1, flight2, flight3, flight4);

        User user1 = new User("ana@gmail.com", "Ana", "test1");
        user1.addFlight(flight1);
        user1.addFlight(flight2);

        User user2 = new User("mihai@gmail.com", "Mihai", "test2");
        user2.addFlight(flight2);
        user2.addFlight(flight3);

        User user3 = new User("ionut@gmail.com", "Ionut", "test3");
        user3.addFlight(flight4);

        List<User> users = List.of(user1, user2, user3);
        airLineManager.setUsers(users);
        airLineManager.setFlights(flights);

        return airLineManager;
    }
}