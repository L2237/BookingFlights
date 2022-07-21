import constants.Commands;
import services.AirLineManager;
import services.FileReaderService;

public class Main {

    public static void main(String[] args) {
        AirLineManager airLineManager = new AirLineManager();
        FileReaderService readerService = new FileReaderService();
        String command = readerService.readLine();

        while (command != null) {
            String[] commands = command.split(" ");

            Commands action;
            try {
                action = Commands.valueOf(commands[0]);
            } catch (IllegalArgumentException e) {
                action = Commands.INVALID_COMMAND;
            }

            switch (action) {
                case SIGNUP: {
                    airLineManager.signup(commands);
                    break;
                }
                case LOGIN: {
                    airLineManager.login(commands);
                    break;
                }
                case LOGOUT: {
                    airLineManager.logout(commands);
                    break;
                }
                case DISPLAY_MY_FLIGHTS: {
                    airLineManager.displayMyFlights(commands);
                    break;
                }
                case ADD_FLIGHT: {
                    airLineManager.addFlight(commands);
                    break;
                }
                case CANCEL_FLIGHT: {
                    airLineManager.cancelFlight(commands);
                    break;
                }
                case ADD_FLIGHT_DETAILS: {
                    airLineManager.addFlightDetails(commands);
                    break;
                }
                case DELETE_FLIGHT: {
                    airLineManager.deleteFlight(commands);
                    break;
                }
                case DISPLAY_FLIGHTS: {
                    airLineManager.displayFlights(commands);
                    break;
                }
                case PERSIST_FLIGHTS: {
                    airLineManager.persistFlights(commands);
                    break;
                }
                case PERSIST_USERS: {
                    airLineManager.persistUsers(commands);
                    break;
                }
                case INVALID_COMMAND: {
                    airLineManager.invalidCommand(commands);
                    break;
                }
            }
            command = readerService.readLine();
        }

        // se termina aplicatia -> flush() -> datele sa fie scrise in fisierul de output
        airLineManager.flush();
    }
}
