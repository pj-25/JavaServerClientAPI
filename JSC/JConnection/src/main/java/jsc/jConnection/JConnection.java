package jsc.jConnection;

import jsc.jEventManager.JEventManager;
import jsc.jMessageHandler.JMessageConsumer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Joshi Prashant
 */
public class JConnection implements JMessageConsumer {

    private String connectionID;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean isConnected = false;

    private JMessageConsumer messageDecoder;

    public JConnection(Socket socket, JMessageConsumer msgDecoder) throws IOException{
        connect(socket);
        messageDecoder = msgDecoder;
    }

    public JConnection(Socket socket) throws IOException {
        this(socket, new JEventManager());
    }

    public JConnection(){
        messageDecoder = new JEventManager();
    }

    public JConnection(String connectionIP, int connectionPort, JMessageConsumer msgDecoder) throws IOException{
        this(new Socket(connectionIP, connectionPort), msgDecoder);
    }

    public JConnection(String connectionIP, int connectionPort) throws IOException {
        this(connectionIP, connectionPort, new JEventManager());
    }

    public JConnection(String connectionID, Socket socket) throws IOException {
        this(connectionID, socket, new JEventManager());
    }

    public JConnection(String connectionID, Socket socket, JMessageConsumer msgDecoder) throws IOException {
        this(socket, msgDecoder);
        setConnectionID(connectionID);
    }

    public void connect(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        isConnected = true;
    }

    public void run(){
        run(this);
    }

    public void run(JMessageConsumer msgDecoder){
        new Thread(()->{
            isConnected = true;
            try{
                while(isConnected){
                    msgDecoder.accept(read());
                }
            }catch(IOException e){
                isConnected = false;
                close();
                System.err.println(getConnectionID()+" disconnected!");
            }
        }).start();
    }

    public String read() throws IOException{
        return dataInputStream.readUTF();
    }

    public void write(String data){
        try{
            dataOutputStream.writeUTF(data);
        }catch (IOException e){
            System.err.println("Unable to send data: "+data);
        }
    }

    public String getConnectionID() {
        return this.connectionID != null ? this.connectionID : "anonymous";
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        connect(socket);
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public JMessageConsumer getMessageDecoder() {
        return messageDecoder;
    }

    public void setMessageDecoder(JMessageConsumer messageDecoder) {
        this.messageDecoder = messageDecoder;
    }


    /**
     * Only applicable when messageDecoder is set to @see {@link jsc.jEventManager.JEventManager}
     * @return JEventConsumer
     */
    public JEventManager getJEventManager(){
        if(messageDecoder instanceof JEventManager){
            return (JEventManager) messageDecoder;
        }
        return null;
    }

    public void accept(String msg) {
        if(messageDecoder!=null){
            messageDecoder.accept(msg);
        }
    }

    public void close(){
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
