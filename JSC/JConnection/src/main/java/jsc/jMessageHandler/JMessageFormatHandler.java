package jsc.jMessageHandler;


import jsc.jEventManager.JEventCode;
import jsc.jEventManager.JEventType;
import jsc.jObjectParser.JObjectParser;

import java.util.ArrayList;

public class JMessageFormatHandler {
    final private static JMessageDelimiter DEFAULT_DELIMITER = JMessageDelimiter.OBJ_ATTRIBUTES_DELIMITER;

    public static String encode(JMessageDelimiter sep, String... msgData){
        if(msgData.length>0){
            StringBuilder msg = new StringBuilder(msgData[0]);
            for(int i=1;i<msgData.length;i++){
                msg.append(sep.toString()).append(msgData[i]);
            }
            return msg.toString();
        }
        return "";
    }

    public static String encode(String... msgData){
        return encode(DEFAULT_DELIMITER, msgData);
    }

    //encode format--> MESSAGE_TYPE:MSG_DATA
    public static String encode(JMessageDelimiter sep, JMessageCode msgType, String...msgData){
        return msgType.getCode()+ JMessageDelimiter.REQUEST_TYPE_DELIMITER.toString() + encode(sep, msgData);
    }

    public static String encode(JEventCode jEventCode, String... eventData){
        return jEventCode.getCode() + JMessageDelimiter.EVENT_TYPE_DELIMITER.toString() + encode(eventData);
    }

    //encode format--> MESSAGE_TYPE{sep}EVENT_TYPE{sep}MSG_DATA
    public static String encode(JMessageDelimiter sep, JMessageType resType, JEventType JEventType, String ...msgData){
        return resType.getCode()+ JMessageDelimiter.RESPONSE_TYPE_DELIMITER.toString() + JEventType.getCode()+ (JMessageDelimiter.EVENT_TYPE_DELIMITER) + encode(sep, msgData);
    }

    //encode format--> MESSAGE_TYPE:EVENT_TYPE:MSG_DATA
    public static String encode(JMessageType msgType, JEventType JEventType, String ...msgData){
        return encode(DEFAULT_DELIMITER , msgType, JEventType, msgData);
    }

    public static String encode(JMessageCode msgType, String ...msgData){
        return encode(DEFAULT_DELIMITER, msgType, msgData);
    }

    public static String encode(ArrayList<? extends JObjectParser> entities){
        if(entities!=null && entities.size()>0 ){
            StringBuilder bookingData = new StringBuilder(entities.get(0).toString());
            for(int i=1;i<entities.size();i++){
                bookingData.append(JMessageDelimiter.OBJ_LIST_DELIMITER).append(JMessageFormatHandler.encode(entities.get(i).toString()));
            }
            return bookingData.toString();
        }
        return "";
    }

    public static String[] decode(String msg){
        return decode( DEFAULT_DELIMITER, msg);
    }

    public static String[] decode(String msg, int limit){
        return decode(DEFAULT_DELIMITER, msg, limit);
    }

    public static String[] decode(JMessageDelimiter sep, String msg){
        return decode(sep, msg, 0);
    }

    public static String[] decode(JMessageDelimiter sep, String msg, int limit){
        return msg.trim().split(sep.toString(), limit);
    }
}
