package jsc.jMessageHandler;

import java.util.HashMap;

public enum JMessageType implements JMessageCode {

    UNAUTHORIZED_USER(-2),
    INVALID_MSG(-1),
    FAILED(0),
    SUCCESS(1),
    READ_INTERRUPT(2),
    FILE_TRANSFER(3),
    GET(3),
    PUT(4),
    POST(5),
    DELETE(6),
    REGISTER(7),
    LOGIN(8)
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
