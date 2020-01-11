package bg.sofia.uni.fmi.chat.nio.client;

import java.io.BufferedReader;
import java.io.IOException;

import static bg.sofia.uni.fmi.chat.nio.client.GlobalConstants.ERROR_READ_DATA_FROM_SERVER_MESSAGE_FORMAT;

public class ClientRunnable implements Runnable {
    private final BufferedReader reader;

    public ClientRunnable(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.printf(ERROR_READ_DATA_FROM_SERVER_MESSAGE_FORMAT);
        }
    }
}
