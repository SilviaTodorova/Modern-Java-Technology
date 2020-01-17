package bg.sofia.uni.fmi.chat.nio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.BUFFER_SIZE;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.DISCONNECT_FROM_SERVER_MESSAGE_FORMAT;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.HOST;
import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.PORT;

public class ClientRunnable implements Runnable {
    private final SocketChannel socket;

    public ClientRunnable(SocketChannel socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true) {
                buffer.clear();
                socket.read(buffer);
                buffer.flip();
                String reply = new String(buffer.array(), 0, buffer.limit());
                System.out.print(reply);
            }
        } catch (IOException e) {
            System.out.printf(DISCONNECT_FROM_SERVER_MESSAGE_FORMAT, HOST, PORT);
        }
    }
}
