package bg.sofia.uni.fmi.chat.nio.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.HOST;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.PORT;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ChatClient {
    public static void main(String[] args) {
        new ChatClient().run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in);
             SocketChannel socketChannel = SocketChannel.open();
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, UTF_8), true);
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, UTF_8))) {

            socketChannel.connect(new InetSocketAddress(HOST, PORT));
            // System.out.printf(CONNECT_TO_SERVER_MESSAGE_FORMAT, HOST, PORT);

            ClientRunnable clientRunnable = new ClientRunnable(reader);
            new Thread(clientRunnable).start();

            while (true) {
                String command = scanner.nextLine();
                writer.println(command);
            }

        } catch (Exception ex) {
            System.out.printf(ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT, HOST, PORT);
        }
    }
}

