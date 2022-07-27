import constants.Commands;
import services.AirLineManager;
import services.AirlineStatistics;
import services.FileReaderService;

public class Main {

    public static void main(String[] args) {

        AirLineManager airLineManager = new AirLineManager();
        AirlineStatistics airlineStatistics = new AirlineStatistics();
        FileReaderService readerService = new FileReaderService();

        String command = readerService.readLine();

        while (command != null) {
            String[] commandParams = command.split(" ");

            Commands action;
            try {
                action = Commands.valueOf(commandParams[0]);
            } catch (IllegalArgumentException e) {
                action = Commands.INVALID_COMMAND;
            }

            switch (action) {
                case SIGNUP: {
                    airLineManager.signup(commandParams);
                    break;
                }
                case LOGIN: {
                    airLineManager.login(commandParams);
                    break;
                }
                case LOGOUT: {
                    airLineManager.logout(commandParams);
                    break;
                }
                case DISPLAY_MY_FLIGHTS: {
                    airLineManager.displayMyFlights(commandParams);
                    break;
                }
                case ADD_FLIGHT: {
                    airLineManager.addFlight(commandParams);
                    break;
                }
                case CANCEL_FLIGHT: {
                    airLineManager.cancelFlight(commandParams);
                    break;
                }
                case ADD_FLIGHT_DETAILS: {
                    airLineManager.addFlightDetails(commandParams);
                    break;
                }
                case DELETE_FLIGHT: {
                    airLineManager.deleteFlight(commandParams);
                    break;
                }
                case DISPLAY_FLIGHTS: {
                    airLineManager.displayFlights(commandParams);
                    break;
                }
                case PERSIST_FLIGHTS: {
                    airLineManager.persistFlights(commandParams);
                    break;
                }
                case PERSIST_USERS: {
                    airLineManager.persistUsers(commandParams);
                    break;
                }
                case INVALID_COMMAND: {
                    airLineManager.invalidCommand(commandParams);
                    break;
                }
            }
            command = readerService.readLine();
        }

        // se termina aplicatia -> flush() -> datele sa fie scrise in fisierul de output
        airLineManager.flush();

        // Display statistics
        AirlineStatistics.findMostUsedCityAsDepartureForFlights(airLineManager);
        AirlineStatistics.findShortestFlight(airLineManager);
    }
}
