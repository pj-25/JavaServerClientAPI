package jsc.jServer;


import jsc.jConnection.JConnection;

abstract public class JRequestManager extends JConnection implements JResponseSender {

    private JClientConnectionsHandler JClientConnectionsHandler = new JClientConnectionsHandler();

    @Override
    abstract public void accept(String req);

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
