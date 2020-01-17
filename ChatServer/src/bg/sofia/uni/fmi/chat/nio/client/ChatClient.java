package bg.sofia.uni.fmi.chat.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.HOST;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.PORT;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.BUFFER_SIZE;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT;

public class ChatClient {
    public static void main(String[] args) {
        new ChatClient().run();
    }

    public void run() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(HOST, PORT));
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            ClientRunnable clientRunnable = new ClientRunnable(socketChannel);
            new Thread(clientRunnable).start();

            while (true) {
                String command = scanner.nextLine();

                buffer.clear();
                buffer.put(command.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
            }

        } catch (Exception ex) {
            System.out.printf(ERROR_CONNECT_TO_SERVER_MESSAGE_FORMAT, HOST, PORT);
        }
    }
}

