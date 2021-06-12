package jsc.jEventManager;

import jsc.jMessageHandler.JMessageConsumer;
import jsc.jMessageHandler.JMessageDelimiter;
import jsc.jMessageHandler.JMessageFormatHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JEventManager implements JMessageConsumer {

    private HashMap<Integer, List<JEventConsumer>> eventMap = new HashMap<>();

    private JMessageConsumer messageConsumer;

    public JEventManager(){}

    public JEventManager(JMessageConsumer jMessageConsumer){
        this.messageConsumer = jMessageConsumer;
    }

    @Override
    public void accept(String msg){
        if(messageConsumer!=null){
            messageConsumer.accept(msg);
        }else{
            String[] eventData = JMessageFormatHandler.decode(JMessageDelimiter.EVENT_TYPE_DELIMITER, msg, 2);
            int eventTypeCode = Integer.parseInt(eventData[0]);
            notifyAllConsumers(eventTypeCode, eventData[1]);
        }
    }

    public void notifyAllConsumers(int eventTypeCode, String eventData){
        List<JEventConsumer> consumers = getAllEventConsumers(eventTypeCode);
        if(consumers!=null){
            for(JEventConsumer jEventConsumer: consumers){
                jEventConsumer.accept(eventData);
            }
        }
    }

    public void notifyAllConsumers(JEventCode eventCode, String eventData){
        notifyAllConsumers(eventCode.getCode(), eventData);
    }

    public void register(JEventCode jEventCode, JEventConsumer eventConsumer){
        if(!eventMap.containsKey(jEventCode.getCode())){
            eventMap.put(jEventCode.getCode(), new ArrayList<>());
        }
        getAllEventConsumers(jEventCode).add(eventConsumer);
    }

    public void registerAll(JEventCode jEventCode, JEventConsumer ... eventConsumers){
        for(JEventConsumer jEventConsumer:eventConsumers){
            register(jEventCode, jEventConsumer);
        }
    }

    public void remove(JEventCode jEventCode, JEventConsumer jEventConsumer){
        getAllEventConsumers(jEventCode).remove(jEventConsumer);
    }

    public void removeAll(JEventCode jEventCode){
        eventMap.remove(jEventCode.getCode());
    }

    public List<JEventConsumer> getAllEventConsumers(int i){
        return eventMap.get(i);
    }

    public List<JEventConsumer> getAllEventConsumers(JEventCode i){
        return getAllEventConsumers(i.getCode());
    }

    public HashMap<Integer, List<JEventConsumer>> getEventMap() {
        return eventMap;
    }

    public JMessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(JMessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setEventMap(HashMap<Integer, List<JEventConsumer>> eventMap) {
        this.eventMap = eventMap;
    }

}
