package bg.sofia.uni.fmi.chat.net;

import bg.sofia.uni.fmi.chat.nio.client.ChatClient;
import bg.sofia.uni.fmi.chat.nio.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ChatClientTest {
    @Mock
    private ChatServer chatServer;

    private ChatClient chatClient;
    private static Thread clientStarterThread;

    @Before
    public void setup() {
        chatServer = Mockito.mock(ChatServer.class);

        clientStarterThread = new Thread() {
            public void run() {
                chatClient = new ChatClient();
                chatClient.run();
            }
        };

        clientStarterThread.start();
    }

    @Test
    public void sendRequest() {
        Mockito.doNothing().when(chatServer).run();
        chatClient.run();
    }


}