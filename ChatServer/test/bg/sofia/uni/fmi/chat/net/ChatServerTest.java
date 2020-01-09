package bg.sofia.uni.fmi.chat.net;

import java.io.UnsupportedEncodingException;

import bg.sofia.uni.fmi.chat.net.client.ChatClient;
import bg.sofia.uni.fmi.chat.net.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ChatServerTest {

    private static ChatServer chatServer;
    private static Thread serverStarterThread;

    @Before
    public void setup() {
        serverStarterThread = new Thread() {

            public void run() {
                chatServer = new ChatServer();
                chatServer.run();
            }
        };

        serverStarterThread.start();
    }

    @Test
    public void testServer() throws UnsupportedEncodingException {
        ChatClient chatClient = Mockito.mock(ChatClient.class);
        Mockito.doNothing().when(chatClient).connect("ivan");
    }
}

