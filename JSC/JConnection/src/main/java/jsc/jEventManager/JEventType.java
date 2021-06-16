package jsc.jEventManager;

import java.util.HashMap;

public enum JEventType implements JEventCode{

    FAILED(0),
    SUCCESS(1),
    ENTITY_TRANSFER(2),
    CANCELED(3),
    NOT_CANCELED(4),
    UPDATED(4),
    NOT_UPDATED(5),
    ENTITY_LIST_TRANSFER(6),
    CLOSE(7),
    ;

    final private int EVENT_CODE;

    JEventType(int eventCode){
        this.EVENT_CODE = eventCode;
    }

    @Override
    public int getCode(){
        return EVENT_CODE;
    }

    private static HashMap<Integer, JEventType> eventTypeMap = new HashMap<>();

    static{
        for(JEventType JEventType : JEventType.values()){
            eventTypeMap.put(JEventType.getCode(), JEventType);
        }
    }

    static public JEventType get(int i){
        return eventTypeMap.get(i);
    }
}
