package bg.sofia.uni.fmi.chat.net;

import java.io.IOException;

import bg.sofia.uni.fmi.chat.net.client.ChatClient;
import bg.sofia.uni.fmi.chat.net.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ChatClientTest {

    @Mock
    private ChatServer chatServer;

    private ChatClient chatClient;

    @Before
    public void setup() throws IOException {
        chatClient = new ChatClient();
        chatClient.run();
        chatServer = Mockito.mock(ChatServer.class);

    }

    @Test(expected = IOException.class)
    public void sendRequest() {
      chatClient.connect("nick Maria");
    }
}