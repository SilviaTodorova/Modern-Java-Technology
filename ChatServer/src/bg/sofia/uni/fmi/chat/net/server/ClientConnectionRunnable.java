package bg.sofia.uni.fmi.chat.net.server;


import bg.sofia.uni.fmi.chat.net.server.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.*;

class ClientConnectionRunnable implements Runnable {
    private String username;
    private Socket socket;

    public ClientConnectionRunnable(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String commandInput = reader.readLine();
                System.out.println(commandInput);

                if (commandInput != null) {
                    String[] tokens = commandInput.split(" ");
                    CommandFactory factory = new CommandFactory();

                    String command = tokens[COMMAND_INDEX];
                    Command cmd = factory.createCommand(command, socket, username, tokens);
                    String response = cmd.execute();
                    writer.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
