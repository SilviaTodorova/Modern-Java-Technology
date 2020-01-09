package bg.sofia.uni.fmi.chat.net.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientRunnable implements Runnable {

    private Socket socket;

    public ClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                if (socket.isClosed()) {
                    System.out.println("client is closed, stop waiting for server messages");
                    return;
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);

                    if (line.contains("disconnected")) {
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
