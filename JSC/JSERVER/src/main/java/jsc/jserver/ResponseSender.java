package jsc.jserver;

@FunctionalInterface
public interface ResponseSender {
    void send(String res);
}
