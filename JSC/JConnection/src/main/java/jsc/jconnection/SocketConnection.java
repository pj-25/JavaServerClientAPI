package jsc.jconnection;

import jsc.jconnection.messageHandler.MessageConsumer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketConnection implements MessageConsumer {

    private String connectionID;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private boolean isConnected = false;

    private MessageConsumer messageDecoder;

    public SocketConnection(Socket socket, MessageConsumer msgDecoder) throws IOException{
        connect(socket);
        messageDecoder = msgDecoder;
    }

    public SocketConnection(){}

    public SocketConnection(String connectionIP, int connectionPort, MessageConsumer msgDecoder) throws IOException{
        this(new Socket(connectionIP, connectionPort), msgDecoder);
    }

    public SocketConnection(String connectionID, Socket socket, MessageConsumer msgDecoder) throws IOException {
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

    public void run(MessageConsumer msgDecoder){
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

    public MessageConsumer getMessageDecoder() {
        return messageDecoder;
    }

    public void setMessageDecoder(MessageConsumer messageDecoder) {
        this.messageDecoder = messageDecoder;
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
