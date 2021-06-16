package jsc.jMessageHandler;

@FunctionalInterface
public interface JMessageSender {
    void send(String msg);
}
