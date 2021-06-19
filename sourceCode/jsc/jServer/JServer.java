package jsc.jServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class JServer implements JSocketConsumer {
    public final static int DEFAULT_SERVER_PORT = 5656;     //Default port number

    private ServerSocket serverSocket;
    private JSocketConsumer JSocketConsumer;

    private Class<? extends JRequestManager> requestHandler;

    private boolean isRunning = false;

    public JServer(int serverPort, Class<? extends JRequestManager> requestHandler) throws IOException {
        this(serverPort,socket-> {
            try{
                JRequestManager JRequestManagerObj =  createRequestHandler(requestHandler);
                JRequestManagerObj.connect(socket);
                JRequestManagerObj.run();
            }catch (RequestHandlerNotFound requestHandlerNotFound){
                requestHandlerNotFound.printStackTrace();
                System.exit(0);
            }
        });
        this.requestHandler = requestHandler;
    }

    static public JRequestManager createRequestHandler(Class< ? extends JRequestManager> requestHandler) throws RequestHandlerNotFound{
        try {
            return requestHandler.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RequestHandlerNotFound(e.getMessage());
        }
    }

    public JServer(int serverPort) throws IOException{
        this(serverPort, (JSocketConsumer) null);
    }

    public JServer() throws IOException{
        this(DEFAULT_SERVER_PORT, (JSocketConsumer) null);
    }

    public JServer(Class<? extends JRequestManager> requestHandler) throws IOException{
        this(DEFAULT_SERVER_PORT, requestHandler);
    }

    public JServer(int serverPort, JSocketConsumer JSocketConsumer) throws IOException{
        serverSocket = new ServerSocket(serverPort);
        this.JSocketConsumer = JSocketConsumer;
    }

    public static int getDefaultServerPort() {
        return DEFAULT_SERVER_PORT;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Class<? extends JRequestManager> getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(Class<? extends JRequestManager> requestHandler) {
        this.requestHandler = requestHandler;
    }

    public JSocketConsumer getServeClient() {
        return JSocketConsumer;
    }

    public void setServeClient(JSocketConsumer JSocketConsumer) {
        this.JSocketConsumer = JSocketConsumer;
    }

    public void start(){
        System.out.println("Server started on "+ getDefaultServerPort()+"...");
        isRunning = true;
        while(isRunning){
            try{
                Socket cSock = serverSocket.accept();
                serve(cSock);
            }
            catch (IOException e){
                System.err.println("Unable to accept client!");
            }
        }
        close();
    }

    public void close(){
        try{
            serverSocket.close();
            isRunning = false;
            System.out.println("Server closed successfully!");
        }catch (IOException ignore){
        }
    }


    @Override
    public void serve(Socket socket) {
        if(JSocketConsumer !=null){
            new Thread(()->{
                try {
                    System.out.println("Socket accepted: "+socket);
                    JSocketConsumer.serve(socket);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }).start();
        }
    }

    public static class RequestHandlerNotFound extends Exception{
        public RequestHandlerNotFound(String errorMsg){
            super(errorMsg);
        }
    }
}
