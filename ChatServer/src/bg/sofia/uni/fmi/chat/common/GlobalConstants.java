package bg.sofia.uni.fmi.chat.common;

import java.time.format.DateTimeFormatter;

public class GlobalConstants {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static final String CONNECT_COMMAND = "nick";
    public static final String DISCONNECT_COMMAND = "disconnect";
    public static final String HOST = "localhost";
    public static final int PORT = 8080;

    public static final int COMMAND_INDEX = 0;
    public static final int USERNAME_INDEX = 1;
    public static final int TO_USERNAME_INDEX = 1;
}
