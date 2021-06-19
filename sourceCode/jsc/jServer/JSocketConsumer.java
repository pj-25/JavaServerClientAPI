package jsc.jServer;

import java.io.IOException;
import java.net.Socket;

@FunctionalInterface
public interface JSocketConsumer {
    void serve(Socket socket) throws IOException;
}
