package bg.sofia.uni.fmi.chat.nio.client;

class GlobalConstants {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;
    public static final int BUFFER_SIZE = 1024;

    public static final String ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT =
            "=> cannot connect to server on %s:%s, make sure that the server is started%n";
    public static final String DISCONNECT_FROM_SERVER_MESSAGE_FORMAT =
            "=> disconnected from server on %s:%s%n";
}
