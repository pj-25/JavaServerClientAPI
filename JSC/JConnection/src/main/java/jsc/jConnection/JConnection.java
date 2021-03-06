package jsc.jConnection;

import jsc.jMessageHandler.JMessageConsumer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Joshi Prashant
 */
public class JConnection implements JMessageConsumer, JCloseEventConsumer {

    private String connectionID;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean isConnected = false;

    private final Object interruptReadLock = new Object();
    private boolean isReadInterrupted = false;

    private JMessageConsumer messageDecoder;

    public JConnection(Socket socket, JMessageConsumer msgDecoder) throws IOException{
        connect(socket);
        messageDecoder = msgDecoder;
    }

    public JConnection(Socket socket) throws IOException {
        this(socket, null);
    }

    public JConnection(){
    }

    public JConnection(String connectionIP, int connectionPort, JMessageConsumer msgDecoder) throws IOException{
        this(new Socket(connectionIP, connectionPort), msgDecoder);
    }


    public JConnection(String connectionIP, int connectionPort) throws IOException {
        this(connectionIP, connectionPort, null);
    }

    public JConnection(String connectionID, Socket socket) throws IOException {
        this(connectionID, socket, null);
    }

    public JConnection(String connectionID, Socket socket, JMessageConsumer msgDecoder) throws IOException {
        this(socket, msgDecoder);
        setConnectionID(connectionID);
    }

    public JConnection(JMessageConsumer msgDecoder) {
        setMessageDecoder(msgDecoder);
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
                    checkIfReadInterrupted();
                    msgDecoder.accept(read());
                }
            }catch(IOException e){
                if(isConnected){
                    close();
                }
                System.err.println(getConnectionID()+" disconnected!");
            }
        }).start();
    }

    public String read() throws IOException{
        return dataInputStream.readUTF();
    }

    private void checkIfReadInterrupted(){
        synchronized (interruptReadLock){
            while (isReadInterrupted()){
                try{
                    interruptReadLock.wait();
                }catch (InterruptedException readWaitInterruptException){
                    readWaitInterruptException.printStackTrace();
                }
            }
        }
    }

    public void setReadInterrupt(boolean readInterruptStatus){
        setIsReadInterrupted(readInterruptStatus);
        if(!readInterruptStatus){
            resumeRead();
        }
    }

    private void resumeRead(){
        synchronized (interruptReadLock){
            setIsReadInterrupted(false);
            interruptReadLock.notifyAll();
        }
    }

    public boolean isReadInterrupted() {
        return isReadInterrupted;
    }

    private void setIsReadInterrupted(boolean readInterrupted) {
        isReadInterrupted = readInterrupted;
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

    public void accept(String msg) {
        if(messageDecoder!=null){
            messageDecoder.accept(msg);
        }
    }

    public void close(){
        try{
            isConnected = false;
            socket.close();
        }catch (IOException e){
            System.err.println(e);
        }
    }

}
