package bg.sofia.uni.fmi.chat.nio.server.commands;

import bg.sofia.uni.fmi.chat.nio.server.ChatServer;

import java.nio.channels.SocketChannel;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.*;

public class Disconnect extends CommandBase {

    public Disconnect(String username, SocketChannel socket) {
        super(username, socket);
    }

    @Override
    public String execute() {
        SocketChannel socket = getSocket();
        String username = getUsername();
        try {
            if (!ChatServer.isUserConnected(socket)) {
                return String.format(ERROR_MESSAGE_DISCONNECT);
            }

            ChatServer.removeUser(socket);

            // Server
            String serverMessage = String.format(USER_DISCONNECTED_MESSAGE_FORMAT, username);
            System.out.print(serverMessage);

            // Client
            return String.format(DISCONNECT_MESSAGE_FORMAT, HOST, PORT);
        } catch (Exception ex) {
            // Server
            String serverMessage = String.format(ERROR_MESSAGE_DISCONNECT_FORMAT, username);
            System.out.print(serverMessage);

            // Client
            return String.format(ERROR_MESSAGE_CLIENT_DISCONNECT);
        }

    }
}


