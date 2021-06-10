package jsc.jserver;

import jsc.jconnection.SocketConnection;

import java.util.HashMap;

public class ClientConnectionsHandler extends HashMap<String, SocketConnection> {
    public void closeAll(){
        for(SocketConnection socketConnection:values()){
            if(socketConnection.isConnected()){
                try{
                    socketConnection.getSocket().close();
                }catch (Exception ignore){
                }
            }
        }
    }

}
