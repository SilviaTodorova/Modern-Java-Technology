package bg.sofia.uni.fmi.chat.nio.server.commands;

import bg.sofia.uni.fmi.chat.nio.server.ChatServer;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.GET_LIST_OF_USERS_MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.USERS_OFFLINE_MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.DELIMITER_USERS;

public class ListUsers extends CommandBase {

    public ListUsers(String username, SocketChannel socket) {
        super(username, socket);
    }

    @Override
    public String execute() {
        String username = getUsername();

        List<String> connectedUsers = new ArrayList<>(ChatServer.getConnectedUsers());
        System.out.print(String.format(GET_LIST_OF_USERS_MESSAGE_FORMAT, username));

        if (connectedUsers.isEmpty()) {
            return String.format(USERS_OFFLINE_MESSAGE_FORMAT);
        }

        return String.join(DELIMITER_USERS, connectedUsers) + System.lineSeparator();
    }
}
