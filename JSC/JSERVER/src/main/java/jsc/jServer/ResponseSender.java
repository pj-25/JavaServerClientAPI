package jsc.jServer;

@FunctionalInterface
public interface ResponseSender {
    void send(String res);
}
