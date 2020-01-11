package bg.sofia.uni.fmi.chat.net;

import bg.sofia.uni.fmi.chat.nio.client.ChatClient;
import bg.sofia.uni.fmi.chat.nio.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ChatServerTest {
    @Mock
    private ChatClient chatClient;

    private static ChatServer chatServer;
    private static Thread serverStarterThread;

    @Before
    public void setup() {
        chatClient = Mockito.mock(ChatClient.class);

        serverStarterThread = new Thread() {

            public void run() {
                chatServer = new ChatServer();
                chatServer.run();
            }
        };

        serverStarterThread.start();
    }

    @Test
    public void testServer() {
        ChatClient chatClient = Mockito.mock(ChatClient.class);
        Mockito.doNothing().when(chatClient).run();
    }
}

