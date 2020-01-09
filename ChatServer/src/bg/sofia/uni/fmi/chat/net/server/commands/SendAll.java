package bg.sofia.uni.fmi.chat.net.server.commands;

import bg.sofia.uni.fmi.chat.net.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Set;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.FORMATTER;

public class SendAll implements Command {
    private Socket socket;
    private String username;
    private String[] parameters;

    public SendAll(Socket socket, String username, String[] parameters) {
        this.socket = socket;
        this.username = username;
        this.parameters = parameters;
    }

    @Override
    public String execute() throws IOException {
        String response = "";
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < parameters.length; i++) {
            message.append(parameters[i]).append(" ");
        }

        Set<String> allUsers = ChatServer.getConnectedUsers().keySet();
        if (allUsers.size() == 1) {
            response = "There aren't another users";
        } else {

            for (String user : allUsers) {

                Socket s = ChatServer.getUser(user);
                if (!s.equals(socket)) {
                    PrintWriter toWriter = new PrintWriter(s.getOutputStream(), true);
                    toWriter.println(
                            String.format("[%s] %s: %s", LocalDateTime.now().format(FORMATTER), username, message));
                }
            }
        }

        return response;
    }
}
