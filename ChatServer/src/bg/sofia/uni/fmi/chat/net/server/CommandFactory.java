package bg.sofia.uni.fmi.chat.net.server;

import bg.sofia.uni.fmi.chat.net.server.commands.*;
import bg.sofia.uni.fmi.chat.net.server.commands.enums.CommandType;

import java.net.Socket;

class CommandFactory {
    public Command createCommand(String commandName, Socket socket, String username, String[] tokens) {
        CommandType commandType = CommandType.valueOf(commandName.replaceAll("[^\\w]", "").toUpperCase());
        switch (commandType) {
            case SEND:
                return new Send(username, tokens);

            case SENDALL:
                return new SendAll(socket, username, tokens);

            case LISTUSERS:
                return new ListUsers(username, tokens);

            case DISCONNECT:
                return new Disconnect(socket, username);

            default:
                throw new IllegalArgumentException("Command doesn't exist! Please, try again.");
        }
    }
}
