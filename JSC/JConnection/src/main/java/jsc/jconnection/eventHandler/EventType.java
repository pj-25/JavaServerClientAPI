package jsc.jconnection.eventHandler;


import jsc.jconnection.messageHandler.MessageCode;

import java.util.HashMap;

public enum EventType implements MessageCode {


    FAILED(0),
    SUCCESS(1),
    ENTITY_TRANSFER(2),
    CANCELED(3),
    NOT_CANCELED(4),
    UPDATED(4),
    NOT_UPDATED(5),
    ENTITY_LIST_TRANSFER(6),
    ;

    final private int EVENT_CODE;

    EventType(int eventCode){
        this.EVENT_CODE = eventCode;
    }

    @Override
    public int getCode(){
        return EVENT_CODE;
    }

    private static HashMap<Integer, EventType> eventTypeMap = new HashMap<>();

    static{
        for(EventType eventType: EventType.values()){
            eventTypeMap.put(eventType.getCode(), eventType);
        }
    }

    static public EventType get(int i){
        return eventTypeMap.get(i);
    }
}
