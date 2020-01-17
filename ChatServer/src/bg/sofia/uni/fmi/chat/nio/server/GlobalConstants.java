package bg.sofia.uni.fmi.chat.nio.server;


import java.time.format.DateTimeFormatter;

public class GlobalConstants {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;
    public static final int SLEEP_MILLIS = 200;
    public static final int BUFFER_SIZE = 1024;

    public static final int COMMAND_INDEX = 0;
    public static final int USERNAME_INDEX = 1;
    public static final int TO_USERNAME_INDEX = 1;

    public static final String CONNECT_COMMAND = "nick";
    public static final String DISCONNECT_COMMAND = "disconnect";
    public static final String SEND_COMMAND = "send";
    public static final String SEND_ALL_COMMAND = "send-all";
    public static final String LIST_USERS_COMMAND = "list-users";

    public static final String DELIMITER = " ";
    public static final String DELIMITER_USERS = ", ";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static final String INVALID_NUMBER_OF_ARGUMENTS_FORMAT =
            "Invalid number of arguments. Expected: %d, Received: %d%n";
    public static final String ERROR_MESSAGE_NULL_VALUE = "Null value";
    public static final String ERROR_MESSAGE_INVALID_USERNAME = "Invalid username";

    // Client Responses
    public static final String CONNECT_TO_SERVER_MESSAGE_FORMAT = "connect %s:%d %s%n";
    public static final String NICK_MESSAGE_FORMAT = "please, first enter nickname%n";
    public static final String USERS_OFFLINE_MESSAGE_FORMAT = "=> nobody is online%n";
    public static final String USER_OFFLINE_MESSAGE_FORMAT = "=> %s seems to be offline%n";
    public static final String MESSAGE_FORMAT = "[%s] %s: %s%n";
    public static final String SINGLE_USER_MESSAGE = "There aren't another users%n";
    public static final String SEND_MESSAGE = "message is sent%n";
    public static final String SENT_MESSAGE_TO_ALL_USERS_MESSAGE_FORMAT = "%s sent message to all users%n";
    public static final String USER_ALREADY_CONNECTED_MESSAGE_FORMAT = "%s user already connected%n";
    public static final String DISCONNECT_MESSAGE_FORMAT = "=> disconnected from server on %s:%s%n";

    public static final String ERROR_INVALID_COMMAND_FORMAT_MESSAGE = "invalid command%n";
    public static final String ERROR_MESSAGE_CLIENT_DISCONNECT = "=> cannot disconnect%n";
    public static final String ERROR_MESSAGE_DISCONNECT = "=> cannot disconnect, try to connect first%n";

    // Server
    public static final String SERVER_RUNNING_MESSAGE_FORMAT = "server is running on %s:%d%n";
    public static final String GET_LIST_OF_USERS_MESSAGE_FORMAT = "%s wants to get list of users%n";
    public static final String MESSAGE_FROM_TO_FORMAT = "message from %s to %s is sent%n";
    public static final String CONNECT_SUCCESSFULLY_MESSAGE_FORMAT = "%s connect successfully%n";
    public static final String USER_DISCONNECTED_MESSAGE_FORMAT = "%s disconnected%n";

    public static final String ERROR_READ_CHANNEL_MESSAGE_FORMAT = "error while reading from channel%n";
    public static final String ERROR_SERVER_MESSAGE_FORMAT = "error server%n";
    public static final String ERROR_MESSAGE_DISCONNECT_FORMAT = "%s cannot disconnect%n";
}
