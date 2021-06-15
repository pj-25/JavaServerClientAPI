package jsc.jServer;

import jsc.jConnection.JConnection;

import java.util.HashMap;

public class JClientConnectionsHandler extends HashMap<String, JConnection> {
    public void closeAll(){
        applyToAll((JConnection::onClose));
    }

    public void publish(String msg){
        applyToAll((jConnection)->{
            jConnection.write(msg);
        });
    }

    public void applyToAll(JConnectionHandler jConnectionHandler){
        for(JConnection socketConnection:values()){
            jConnectionHandler.serve(socketConnection);
        }
    }
}
