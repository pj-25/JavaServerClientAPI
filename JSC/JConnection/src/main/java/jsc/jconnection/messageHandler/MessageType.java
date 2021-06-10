package jsc.jconnection.messageHandler;

import java.util.HashMap;

public enum MessageType implements MessageCode {

    UNAUTHORIZED_USER(-2),
    INVALID_MSG(-1),
    FAILED(0),
    SUCCESS(1),
    REGISTER(2),
    LOGIN(3)
    ;

    private final int MESSAGE_CODE;

    MessageType(int responseCode){
        this.MESSAGE_CODE = responseCode;
    }

    public int getCode(){
        return MESSAGE_CODE;
    }

    private static final HashMap<Integer, MessageType> responseTypeMap = new HashMap<>();

    static{
        for(MessageType messageType : MessageType.values()){
            responseTypeMap.put(messageType.getCode(), messageType);
        }
    }

    public static MessageType get(int msgCode) {
        return responseTypeMap.get(msgCode);
    }
}
