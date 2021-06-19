package jsc.jEventManager;

import java.util.HashMap;

public enum JEventType implements JEventCode{

    FAILED(-1),
    SUCCESS(-2),
    ENTITY_TRANSFER(-3),
    CANCELED(-4),
    NOT_CANCELED(-5),
    UPDATED(-6),
    NOT_UPDATED(-7),
    ENTITY_LIST_TRANSFER(-8),
    CONNECT(-9),
    CLOSE(-10),
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
