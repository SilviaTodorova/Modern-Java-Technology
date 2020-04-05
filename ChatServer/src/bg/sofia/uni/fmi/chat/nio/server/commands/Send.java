package bg.sofia.uni.fmi.chat.nio.server.commands;

import bg.sofia.uni.fmi.chat.nio.server.ChatServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.USER_OFFLINE_MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.MESSAGE_FROM_TO_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.DATE_FORMATTER;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.INVALID_NUMBER_OF_ARGUMENTS_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.TO_USERNAME_INDEX;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.DELIMITER;

public class Send extends CommandBase {
    private static final int EXPECTED_MIN_NUMBER_OF_PARAMETERS = 2;

    private String to;
    private String message;

    public Send(String username, SocketChannel socket, String[] parameters) {
        super(username, socket);
        parseParameters(parameters);
    }

    @Override
    public String execute() throws IOException {
        String username = getUsername();

        SocketChannel toSocket = ChatServer.getUserSocket(to);
        if (toSocket == null) {
            return String.format(USER_OFFLINE_MESSAGE_FORMAT, to);
        }

        // Server
        String serverMessage = String.format(
                MESSAGE_FROM_TO_FORMAT,
                username,
                ChatServer.getUsername(toSocket));

        System.out.print(serverMessage);

        // Client
        String clientsMessage = String.format(
                MESSAGE_FORMAT,
                LocalDateTime.now().format(DATE_FORMATTER),
                username,
                message);

        SocketChannel senderSocket = getSocket();
        if (!senderSocket.equals(toSocket)) {
            toSocket.write(ByteBuffer.wrap(clientsMessage.getBytes()));
        }

        return clientsMessage;
    }

    private void parseParameters(String[] parameters) {
        if (parameters.length < EXPECTED_MIN_NUMBER_OF_PARAMETERS) {
            String formattedMessage = String.format(
                    INVALID_NUMBER_OF_ARGUMENTS_FORMAT,
                    EXPECTED_MIN_NUMBER_OF_PARAMETERS,
                    parameters.length);

            throw new IllegalArgumentException(formattedMessage);
        }

        to = parameters[TO_USERNAME_INDEX - 1];
        message = Arrays.stream(parameters).skip(1).collect(Collectors.joining(DELIMITER));
    }
}
