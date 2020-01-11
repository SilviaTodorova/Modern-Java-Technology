package bg.sofia.uni.fmi.chat.nio.server.commands;

import bg.sofia.uni.fmi.chat.nio.server.commands.contracts.Command;

import java.nio.channels.SocketChannel;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.ERROR_MESSAGE_NULL_VALUE;
import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.ERROR_MESSAGE_INVALID_USERNAME;

public abstract class CommandBase implements Command {
    private SocketChannel socket;
    private String username;

    public CommandBase(String username, SocketChannel socket) {
        setUsername(username);
        setSocket(socket);
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NULL_VALUE);
        }

        if (username.length() == 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_USERNAME);
        }

        this.username = username;
    }

    private void setSocket(SocketChannel socket) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NULL_VALUE);
        }

        this.socket = socket;
    }
}
