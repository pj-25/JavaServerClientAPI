package jsc.jServer;

@FunctionalInterface
public interface JResponseSender {
    void send(String res);
}
