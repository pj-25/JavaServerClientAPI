package jsc.jConnection;

import java.io.IOException;

class JConnectionTest {

    JConnection jConnection;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        jConnection = new JConnection("127.0.0.1", 5656, System.out::println);
        jConnection.run();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        jConnection.close();
    }

    @org.junit.jupiter.api.Test
    void setReadInterrupt() throws InterruptedException, IOException {
        jConnection.write("Hello Server!");
        jConnection.setReadInterrupt(true);
        jConnection.write("Have you got my msg???");
        //System.out.println("Manual read:"+jConnection.read());
        jConnection.write("(*-*)");
        Thread.sleep(5000);
        jConnection.setReadInterrupt(false);
        jConnection.write("Bye!");
    }
}