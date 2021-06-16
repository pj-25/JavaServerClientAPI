package jsc.jEventManager;

import jsc.jMessageHandler.JMessageSender;

public interface JEventDataSender extends JMessageSender {
    void send(JEventCode jEvent, String eventData);
}
