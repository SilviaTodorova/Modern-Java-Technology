package bg.sofia.uni.fmi.chat.net.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.chat.common.GlobalConstants.PORT;

public class ChatServer {
    private static Map<String, Socket> users;
    private static Map<String, LocalDateTime> connectedUsers;

    public ChatServer() {
        users = new HashMap<>();
        connectedUsers = new HashMap<>();
    }

    public static Socket getUser(String username) {
        return users.get(username);
    }

    public static Map<String, LocalDateTime> getConnectedUsers() {
        return connectedUsers;
    }

    public static boolean ifUserIsConnected(String username) {
        return users.containsKey(username);
    }

    public static void removeUser(String username) {
        users.remove(username);
        connectedUsers.remove(username);
    }

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.printf("server is running on localhost:%d%n", PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A client connected to the server " + socket.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String username = reader.readLine();
                users.put(username, socket);
                connectedUsers.put(username, LocalDateTime.now());
                System.out.println(username + " connected");

                ClientConnectionRunnable runnable = new ClientConnectionRunnable(username, socket);
                new Thread(runnable).start();

            }

        } catch (IOException e) {
            String message = "Port: %s is occupied!";
            System.out.println(String.format(message, PORT));
        }
    }

    public static void main(String[] args) {
        new ChatServer().run();
    }
}

