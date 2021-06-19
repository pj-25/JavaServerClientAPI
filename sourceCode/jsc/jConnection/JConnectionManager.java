package jsc.jConnection;

import jsc.jEventManager.*;
import jsc.jMessageHandler.JMessageFormatHandler;

import java.io.IOException;
import java.net.Socket;


public class JConnectionManager extends JConnection implements JEventDataSender {

    private JEventManager jEventManager = (JEventManager) getMessageDecoder();

    public JConnectionManager(Socket socket, JEventManager jEventManager) throws IOException {
        super(socket, jEventManager);
    }

    public JConnectionManager(JEventManager jEventManager) {
        super(jEventManager);
    }

    public JConnectionManager(String connectionIP, int connectionPort, JEventManager jEventManager) throws IOException {
        super(connectionIP, connectionPort, jEventManager);
    }

    public JConnectionManager(String connectionID, Socket socket, JEventManager jEventManager) throws IOException {
        super(connectionID, socket, jEventManager);
    }

    public JConnectionManager(Socket socket) throws IOException {
        super(socket, new JEventManager());
    }

    public JConnectionManager() {
        super(new JEventManager());
    }

    public JConnectionManager(String connectionIP, int connectionPort) throws IOException {
        super(connectionIP, connectionPort, new JEventManager());
    }

    public JConnectionManager(String connectionID, Socket socket) throws IOException {
        super(connectionID, socket, new JEventManager());
    }

    public JEventManager getjEventManager() {
        return jEventManager;
    }

    public void setjEventManager(JEventManager jEventManager) {
        this.jEventManager = jEventManager;
    }

    /**
     * registers a close event which will be triggered when connection is closed
     * @param jCloseEventConsumer binds the close event to the provided jCloseEventConsumer
     */
    public void bindOnClose(JCloseEventConsumer jCloseEventConsumer){
        jEventManager.bind(JEventType.CLOSE, jCloseEventConsumer);
    }

    @Override
    public void send(JEventCode eventCode, String eventData) {
        write(JMessageFormatHandler.encode(eventCode, eventData));
    }

    @Override
    public void send(String msg) {
        write(msg);
    }

    @Override
    public void close() {
        super.close();
        jEventManager.notifyAllConsumers(JEventType.CLOSE, "connection closed");
    }
}
