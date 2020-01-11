package bg.sofia.uni.fmi.chat.nio.server.commands.contracts;

import java.io.IOException;

public interface Command {
    String execute() throws IOException;
}
