package bg.sofia.uni.fmi.chat.net.server.commands;

import bg.sofia.uni.fmi.chat.net.server.ChatServer;

import java.io.IOException;
import java.net.Socket;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.HOST;
import static bg.sofia.uni.fmi.chat.common.GlobalConstants.PORT;

public class Disconnect implements Command {
    private Socket socket;
    private String username;

    public Disconnect(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public String execute() throws IOException {
        String response;
        if (ChatServer.ifUserIsConnected(username)) {
            String message = "=> disconnected from server on %s:%s";
            response = String.format(message, HOST, PORT);
            ChatServer.removeUser(username);
            socket.close();
        } else {
            response = "=> cannot disconnect, try to connect first";
        }
        return response;
    }
}
