package bg.sofia.uni.fmi.chat.net.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.*;

public class ChatClient {
    private PrintWriter writer;

    public static void main(String[] args) {
        new ChatClient().run();
    }

    public void run() {

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String input = scanner.nextLine();

                String[] tokens = input.split(" ");
                String command = tokens[COMMAND_INDEX];
                if (command.equals(CONNECT_COMMAND)) {
                    String username = tokens[USERNAME_INDEX];
                    connect(username);
                } else {
                    writer.println(input);

                    if (input.equals(DISCONNECT_COMMAND)) {
                        System.out.println("Closing client");
                        return;
                    }
                }
            }
        }
    }

    public void connect(String username) {
        try {
            Socket socket = new Socket(HOST, PORT);

            writer = new PrintWriter(socket.getOutputStream(), true);

            String message = "=> connected to server running on %s:%s as %s";
            System.out.println(String.format(message, HOST, PORT, username));
            writer.println(username);

            ClientRunnable clientRunnable = new ClientRunnable(socket);
            new Thread(clientRunnable).start();

        } catch (IOException e) {
            String message = "=> cannot connect to server on %s:%s, make sure that the server is started";
            System.out.println(String.format(message, HOST, PORT));
        }
    }
}

