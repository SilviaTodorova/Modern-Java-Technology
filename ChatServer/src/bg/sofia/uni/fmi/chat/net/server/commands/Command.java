package bg.sofia.uni.fmi.chat.net.server.commands;

import java.io.IOException;

public interface Command {
    String execute() throws IOException;
}
