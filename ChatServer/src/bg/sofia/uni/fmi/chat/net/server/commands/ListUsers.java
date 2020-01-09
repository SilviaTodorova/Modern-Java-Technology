package bg.sofia.uni.fmi.chat.net.server.commands;

import bg.sofia.uni.fmi.chat.net.server.ChatServer;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.FORMATTER;

public class ListUsers implements Command {
    private String username;
    private String[] parameters;

    public ListUsers(String username, String[] parameters) {
        this.username = username;
        this.parameters = parameters;
    }

    @Override
    public String execute() {
        StringBuilder response = new StringBuilder();
        Map<String, LocalDateTime> connectedUsers = ChatServer.getConnectedUsers();

        System.out.println(username + " wants to get list of users");

        if (!connectedUsers.isEmpty()) {
            Set<Map.Entry<String, LocalDateTime>> entries = connectedUsers.entrySet();

            for (Map.Entry<String, LocalDateTime> entry : entries) {
                String time = entry.getValue().format(FORMATTER);
                response.append(String.format("=> %s, connected at %s%n", entry.getKey(), time));
            }
        } else {
            response.append("=> nobody is online");
        }

        return response.toString().trim();
    }
}
