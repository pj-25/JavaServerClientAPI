package jsc.jserver;


import jsc.jconnection.SocketConnection;

abstract public class RequestManager extends SocketConnection implements ResponseSender{

    private String response;
    private boolean hasResponse = false;

    private ClientConnectionsHandler clientConnectionsHandler = new ClientConnectionsHandler();

    @Override
    abstract public void accept(String req);

    public String getResponse() {
        return response;
    }

    final public void setResponse(String response) {
        hasResponse = true;
        this.response = response;
    }

    public void send(){
        send(response);
        hasResponse = false;
    }

    @Override
    public void send(String res){
        write(res);
    }

    public ClientConnectionsHandler getClientConnectionsHandler() {
        return clientConnectionsHandler;
    }

    public void setClientConnectionsHandler(ClientConnectionsHandler ccHandler) {
        clientConnectionsHandler = ccHandler;
    }
}
