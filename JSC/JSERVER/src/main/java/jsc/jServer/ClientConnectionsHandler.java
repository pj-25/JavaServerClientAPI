package jsc.jServer;

import jsc.jConnection.JConnection;

import java.util.HashMap;

public class ClientConnectionsHandler extends HashMap<String, JConnection> {
    public void closeAll(){
        applyToAll((JConnection::close));
    }

    public void publish(String msg){
        for(JConnection socketConnection:values()){
            socketConnection.write(msg);
        }
    }

    public void applyToAll(JConnectionHandler jConnectionHandler){
        for(JConnection socketConnection:values()){
            jConnectionHandler.serve(socketConnection);
        }
    }
}
