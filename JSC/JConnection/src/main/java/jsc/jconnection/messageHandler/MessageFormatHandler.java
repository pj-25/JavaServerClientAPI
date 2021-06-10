package jsc.jconnection.messageHandler;


import jsc.jconnection.eventHandler.EventType;
import jsc.jconnection.objectParser.ObjectParser;

import java.util.ArrayList;

public class MessageFormatHandler {
    final private static MessageDelimiter DEFAULT_DELIMITER = MessageDelimiter.OBJ_ATTRIBUTES_DELIMITER;

    public static String encode(MessageDelimiter sep, String... msgData){
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
    public static String encode(MessageDelimiter sep, MessageCode msgType, String...msgData){
        return msgType.getCode()+ MessageDelimiter.REQUEST_TYPE_DELIMITER.toString() + encode(sep, msgData);
    }

    //encode format--> MESSAGE_TYPE{sep}EVENT_TYPE{sep}MSG_DATA
    public static String encode(MessageDelimiter sep, jsc.jconnection.messageHandler.MessageType resType, jsc.jconnection.eventHandler.EventType eventType, String ...msgData){
        return resType.getCode()+ MessageDelimiter.RESPONSE_TYPE_DELIMITER.toString() +eventType.getCode()+ (jsc.jconnection.messageHandler.MessageDelimiter.EVENT_TYPE_DELIMITER) + encode(sep, msgData);
    }

    //encode format--> MESSAGE_TYPE:EVENT_TYPE:MSG_DATA
    public static String encode(MessageType msgType, EventType eventType, String ...msgData){
        return encode(DEFAULT_DELIMITER , msgType, eventType, msgData);
    }

    public static String encode(MessageCode msgType, String ...msgData){
        return encode(DEFAULT_DELIMITER, msgType, msgData);
    }

    public static String encode(ArrayList<? extends ObjectParser> entities){
        if(entities!=null && entities.size()>0 ){
            StringBuilder bookingData = new StringBuilder(entities.get(0).toString());
            for(int i=1;i<entities.size();i++){
                bookingData.append(MessageDelimiter.OBJ_LIST_DELIMITER).append(MessageFormatHandler.encode(entities.get(i).toString()));
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

    public static String[] decode(MessageDelimiter sep, String msg){
        return decode(sep, msg, 0);
    }

    public static String[] decode(MessageDelimiter sep, String msg, int limit){
        return msg.trim().split(sep.toString(), limit);
    }
}
