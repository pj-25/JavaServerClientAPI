package jsc.jEventManager;

import jsc.jMessageHandler.JMessageDelimiter;
import jsc.jMessageHandler.JMessageFormatHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JEventManager implements JEventConsumer {

    private HashMap<Integer, List<JEventConsumer>> eventMap = new HashMap<>();

    private JEventConsumer messageConsumer;

    public JEventManager(){
        setMessageConsumer((msg) ->{
            String[] eventData = JMessageFormatHandler.decode(JMessageDelimiter.EVENT_TYPE_DELIMITER, msg, 2);
            int eventTypeCode = Integer.parseInt(eventData[0]);
            notifyAllConsumers(eventTypeCode, eventData[1]);
        });
    }

    public JEventManager(JEventConsumer jMessageConsumer){
        this.messageConsumer = jMessageConsumer;
    }

    @Override
    public void accept(String msg){
        messageConsumer.accept(msg);
    }

    public void notifyAllConsumers(int eventTypeCode, String eventData){
        List<JEventConsumer> consumers = getAllEventConsumers(eventTypeCode);
        if(consumers!=null){
            for(JEventConsumer jEventConsumer: consumers){
                jEventConsumer.accept(eventData);
            }
        }else{
            System.out.println("No consumer found for "+JEventType.get(eventTypeCode) +"("+eventTypeCode+")");
        }
    }

    public void notifyAllConsumers(JEventCode eventCode, String eventData){
        notifyAllConsumers(eventCode.getCode(), eventData);
    }

    public void bind(JEventCode jEventCode, JEventConsumer eventConsumer){
        if(!eventMap.containsKey(jEventCode.getCode())){
            eventMap.put(jEventCode.getCode(), new ArrayList<>());
        }
        getAllEventConsumers(jEventCode).add(eventConsumer);
    }

    public void bindAll(JEventCode jEventCode, JEventConsumer ... eventConsumers){
        for(JEventConsumer jEventConsumer:eventConsumers){
            bind(jEventCode, jEventConsumer);
        }
    }

    public void unbind(JEventCode jEventCode, JEventConsumer jEventConsumer){
        getAllEventConsumers(jEventCode).remove(jEventConsumer);
    }

    public void unbindAll(JEventCode jEventCode){
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

    public JEventConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(JEventConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setEventMap(HashMap<Integer, List<JEventConsumer>> eventMap) {
        this.eventMap = eventMap;
    }

    @Override
    public String toString() {
        return "JEventManager{" +
                "eventMap=" + eventMap +
                ", messageConsumer=" + messageConsumer +
                '}';
    }
}
