package bg.sofia.uni.fmi.chat.net.server.commands;

import bg.sofia.uni.fmi.chat.net.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.FORMATTER;
import static bg.sofia.uni.fmi.chat.common.GlobalConstants.TO_USERNAME_INDEX;

public class Send implements Command {
    private String username;
    private String[] parameters;

    public Send(String username, String[] parameters) {
        this.username = username;
        this.parameters = parameters;
    }

    @Override
    public String execute() throws IOException {
        String response = "";
        String to = parameters[TO_USERNAME_INDEX];

        StringBuilder message = new StringBuilder();
        for (int i = TO_USERNAME_INDEX + 1; i < parameters.length; i++) {
            message.append(parameters[i]).append(" ");
        }

        Socket toSocket = ChatServer.getUser(to);
        if (toSocket == null) {
            response = String.format("=> %s seems to be offline", to);
        }

        PrintWriter toWriter = new PrintWriter(toSocket.getOutputStream(), true);
        toWriter.println(String.format("[%s] %s: %s", LocalDateTime.now().format(FORMATTER), username, message));

        System.out.println("message is sent");

        return response;
    }
}
