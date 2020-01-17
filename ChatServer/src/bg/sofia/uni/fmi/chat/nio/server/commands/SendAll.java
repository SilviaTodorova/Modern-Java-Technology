package bg.sofia.uni.fmi.chat.nio.server.commands;

import bg.sofia.uni.fmi.chat.nio.server.ChatServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Map;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.DATE_FORMATTER;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.DELIMITER;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.SINGLE_USER_MESSAGE;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.SENT_MESSAGE_TO_ALL_USERS_MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.SEND_MESSAGE;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.INVALID_NUMBER_OF_ARGUMENTS_FORMAT;

public class SendAll extends CommandBase {
    private static final int EXPECTED_MIN_NUMBER_OF_PARAMETERS = 1;

    private String message;

    public SendAll(String username, SocketChannel socket, String[] parameters) {
        super(username, socket);
        parseParameters(parameters);
    }

    @Override
    public String execute() throws IOException {
        SocketChannel socket = getSocket();
        String username = getUsername();

        Map<SocketChannel, String> allUsers = ChatServer.getConnectedUsersSockets();
        if (allUsers.size() == 1) {
            return String.format(SINGLE_USER_MESSAGE);
        }

        for (SocketChannel toSocket : allUsers.keySet()) {
            if (!toSocket.equals(socket)) {
                String formattedMessage = String.format(
                        MESSAGE_FORMAT,
                        LocalDateTime.now().format(DATE_FORMATTER),
                        username,
                        message);

                toSocket.write(ByteBuffer.wrap(formattedMessage.getBytes()));
            }
        }

        // Server
        String serverMessage = String.format(SENT_MESSAGE_TO_ALL_USERS_MESSAGE_FORMAT, username);
        System.out.print(serverMessage);

        // Client
        return String.format(SEND_MESSAGE);
    }

    private void parseParameters(String[] parameters) {
        if (parameters.length < EXPECTED_MIN_NUMBER_OF_PARAMETERS) {
            String formattedMessage = String.format(
                    INVALID_NUMBER_OF_ARGUMENTS_FORMAT,
                    EXPECTED_MIN_NUMBER_OF_PARAMETERS,
                    parameters.length);

            throw new IllegalArgumentException(formattedMessage);
        }

        message = String.join(DELIMITER, parameters);
    }
}
