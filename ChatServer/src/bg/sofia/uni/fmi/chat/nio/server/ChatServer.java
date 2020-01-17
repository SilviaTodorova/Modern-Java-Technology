package bg.sofia.uni.fmi.chat.nio.server;


import bg.sofia.uni.fmi.chat.nio.server.commands.contracts.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;

import static bg.sofia.uni.fmi.chat.nio.server.GlobalConstants.*;


public class ChatServer {

    private static Map<SocketChannel, String> users;

    public ChatServer() {
        users = new HashMap<>();
    }

    public static void main(String[] args) {
        new ChatServer().run();
    }

    public static void removeUser(SocketChannel socket) {
        users.remove(socket);
    }

    public static String getUsername(SocketChannel socket) {
        if (!isUserConnected(socket)) {
            throw new IllegalArgumentException();
        }

        return users.get(socket);
    }

    public static SocketChannel getUserSocket(String username) {
        return users.entrySet().stream()
                .filter(e -> e.getValue().equals(username))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public static boolean isUserConnected(SocketChannel socket) {
        return users.containsKey(socket);
    }

    public static Collection<String> getConnectedUsers() {
        return users.values();
    }

    public static Map<SocketChannel, String> getConnectedUsersSockets() {
        return users;
    }

    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.bind(new InetSocketAddress(HOST, PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            System.out.printf(SERVER_RUNNING_MESSAGE_FORMAT, HOST, PORT);

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    try {
                        Thread.sleep(SLEEP_MILLIS);
                    } catch (InterruptedException e) {
                        System.out.printf(ERROR_READ_CHANNEL_MESSAGE_FORMAT);
                    }

                    continue;
                }

                iterateClients(selector, buffer);
            }

        } catch (Exception ex) {
            System.out.printf(ERROR_SERVER_MESSAGE_FORMAT);
        }
    }

    private static void iterateClients(Selector selector, ByteBuffer buffer) throws IOException {
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();

                // Read
                buffer.clear();
                int r = sc.read(buffer);
                if (r <= 0) {
                    sc.close();
                    break;
                }

                buffer.flip();
                String command = new String(buffer.array(), 0, buffer.limit());

                // Write
                buffer.clear();
                try {

                    String response = executeCommand(command, sc);
                    buffer.put(response.getBytes());

                } catch (Exception ex) {
                    String message = ex.getMessage() != null && !ex.getMessage().isEmpty() ?
                            ex.getMessage() : ex.toString();

                    buffer.put(message.getBytes());
                }

                buffer.flip();
                sc.write(buffer);

                if (command.equals(DISCONNECT_COMMAND)) {
                    sc.close();
                }

            } else if (key.isAcceptable()) {
                ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
                SocketChannel accept = sockChannel.accept();
                accept.configureBlocking(false);
                accept.register(selector, SelectionKey.OP_READ);
            }

            keyIterator.remove();
        }
    }

    private static String executeCommand(String input, SocketChannel socket) throws IOException {
        String[] tokens = input.split(DELIMITER);
        int len = tokens.length;

        if (len < 1) {
            return String.format(ERROR_INVALID_COMMAND_FORMAT_MESSAGE);
        }

        String command = tokens[COMMAND_INDEX];
        if (command.equals(CONNECT_COMMAND) && len != 2) {
            return String.format(ERROR_INVALID_COMMAND_FORMAT_MESSAGE);
        }

        if (command.equals(CONNECT_COMMAND)) {
            // connect
            String username = tokens[USERNAME_INDEX];

            if (users.containsValue(username)) {
                return String.format(USER_ALREADY_CONNECTED_MESSAGE_FORMAT, username);
            }

            users.put(socket, username);

            System.out.printf(CONNECT_SUCCESSFULLY_MESSAGE_FORMAT, username);

            return String.format(CONNECT_TO_SERVER_MESSAGE_FORMAT, HOST, PORT, username);

        } else {
            if (!isUserConnected(socket)) {
                return String.format(NICK_MESSAGE_FORMAT);
            }

            // already connected
            String username = getUsername(socket);
            String[] parameters = Arrays.stream(tokens).skip(1).toArray(String[]::new);

            CommandFactory factory = new CommandFactory();
            Command cmd = factory.createCommand(command, socket, username, parameters);
            return cmd.execute();
        }
    }

}

