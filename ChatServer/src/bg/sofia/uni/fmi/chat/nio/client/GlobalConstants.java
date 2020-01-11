package bg.sofia.uni.fmi.chat.nio.client;

class GlobalConstants {
    public static final String HOST = "localhost";
    public static final int PORT = 8080;

    public static final String ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT =
            "=> cannot connect to server on %s:%s, make sure that the server is started%n";
    public static final String ERROR_READ_DATA_FROM_SERVER_MESSAGE_FORMAT = "=> cannot read from server%n";
    // public static final String CONNECT_TO_SERVER_MESSAGE_FORMAT = "=> connected to server running on %s:%s%n";
}
