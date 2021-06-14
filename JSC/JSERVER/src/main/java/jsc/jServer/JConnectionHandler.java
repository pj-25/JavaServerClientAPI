package jsc.jServer;

import jsc.jConnection.JConnection;

@FunctionalInterface
public interface JConnectionHandler {
    void serve(JConnection socketConnection);
}
