package jsc.jMessageHandler;

public enum JMessageDelimiter {

    REQUEST_TYPE_DELIMITER("/"),
    RESPONSE_TYPE_DELIMITER("~"),
    EVENT_TYPE_DELIMITER(">"),
    OBJ_ATTRIBUTES_DELIMITER(";"),
    OBJ_LIST_DELIMITER(",")
    ;
    private final String delimiter;

    JMessageDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    @Override
    public String toString() {
        return delimiter;
    }
}
