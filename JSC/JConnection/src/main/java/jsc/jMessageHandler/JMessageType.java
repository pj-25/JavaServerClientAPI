package jsc.jMessageHandler;

import java.util.HashMap;

public enum JMessageType implements JMessageCode {

    UNAUTHORIZED_USER(-2),
    INVALID_MSG(-1),
    FAILED(0),
    SUCCESS(1),
    REGISTER(2),
    LOGIN(3)
    ;

    private final int MESSAGE_CODE;

    JMessageType(int responseCode){
        this.MESSAGE_CODE = responseCode;
    }

    public int getCode(){
        return MESSAGE_CODE;
    }

    private static final HashMap<Integer, JMessageType> responseTypeMap = new HashMap<>();

    static{
        for(JMessageType JMessageType : JMessageType.values()){
            responseTypeMap.put(JMessageType.getCode(), JMessageType);
        }
    }

    public static JMessageType get(int msgCode) {
        return responseTypeMap.get(msgCode);
    }
}
