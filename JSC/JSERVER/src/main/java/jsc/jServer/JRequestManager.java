package jsc.jServer;


import jsc.jConnection.JConnection;

abstract public class JRequestManager extends JConnection implements JResponseSender {

    private String response;
    private boolean hasResponse = false;

    private JClientConnectionsHandler JClientConnectionsHandler = new JClientConnectionsHandler();

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

    public JClientConnectionsHandler getClientConnectionsHandler() {
        return JClientConnectionsHandler;
    }

    public void setClientConnectionsHandler(JClientConnectionsHandler ccHandler) {
        JClientConnectionsHandler = ccHandler;
    }
}
