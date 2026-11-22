package jsc.jServer;

import jsc.jConnection.JConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** End-to-end: real JServer on an ephemeral port, real JConnection client, messages echoed back in order. */
class EchoRoundTripTest {

    public static class Echo extends JRequestManager {
        @Override
        public void accept(String req) { write(req); }
    }

    private JServer server;
    private JConnection client;
    private final BlockingQueue<String> received = new LinkedBlockingQueue<>();

    @BeforeEach
    void setUp() throws Exception {
        server = new JServer(0, Echo.class);                 // port 0 = OS picks a free port
        Thread t = new Thread(server::start, "jserver");
        t.setDaemon(true);
        t.start();
        client = new JConnection("127.0.0.1", server.getServerSocket().getLocalPort(), received::add);
        client.run();
    }

    @AfterEach
    void tearDown() {
        client.close();
        server.setRunning(false);
        server.close();
    }

    @Test
    void echoesMessagesInOrder() throws Exception {
        String[] msgs = {"hello", "1/2~3>4;5,6", "unicode ✓"};
        for (String m : msgs) client.write(m);
        for (String m : msgs) assertEquals(m, received.poll(5, TimeUnit.SECONDS));
    }

    @Test
    void pausedReadHoldsMessagesUntilResume() throws Exception {
        client.write("before");
        assertEquals("before", received.poll(5, TimeUnit.SECONDS));
        client.setReadInterrupt(true);
        client.write("m1");
        client.write("m2");
        client.write("m3");
        Thread.sleep(500);
        // The pause is checked before each blocking readUTF(), so a read already in flight
        // can still deliver one message (documented limitation); the rest must be held.
        int leaked = received.size();
        assertTrue(leaked <= 1, "at most one in-flight message may arrive while paused, got " + leaked);
        client.setReadInterrupt(false);
        for (String m : new String[]{"m1", "m2", "m3"}) assertEquals(m, received.poll(5, TimeUnit.SECONDS));
    }
}
