package bg.sofia.uni.fmi.chat.nio.server;

import bg.sofia.uni.fmi.chat.nio.server.commands.*;
import bg.sofia.uni.fmi.chat.nio.server.commands.contracts.Command;

import java.nio.channels.SocketChannel;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.*;

class CommandFactory {

    public Command createCommand(String commandName, SocketChannel socket, String username, String[] tokens) {
        switch (commandName) {
            case SEND_COMMAND:
                return new Send(username, socket, tokens);

            case SEND_ALL_COMMAND:
                return new SendAll(username, socket, tokens);

            case LIST_USERS_COMMAND:
                return new ListUsers(username, socket);

            case DISCONNECT_COMMAND:
                return new Disconnect(username, socket);

            default:
                return null;
        }
    }
}
