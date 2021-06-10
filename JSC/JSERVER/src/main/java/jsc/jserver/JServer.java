package jsc.jserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class JServer implements ServeClient {
    public final static int DEFAULT_SERVER_PORT = 5656;     //Default port number

    private ServerSocket serverSocket;
    private ServeClient serveClient;

    private Class<? extends RequestManager> requestHandler;

    private boolean isRunning = false;

    public JServer(int serverPort, Class<? extends RequestManager> requestHandler) throws IOException {
        this(serverPort,socket-> {
            try{
                RequestManager requestManagerObj =  createRequestHandler(requestHandler);
                requestManagerObj.connect(socket);
                requestManagerObj.run();
            }catch (RequestHandlerNotFound requestHandlerNotFound){
                requestHandlerNotFound.printStackTrace();
                System.exit(0);
            }
        });
        this.requestHandler = requestHandler;
    }

    static public RequestManager createRequestHandler(Class< ? extends RequestManager> requestHandler) throws RequestHandlerNotFound{
        try {
            return requestHandler.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RequestHandlerNotFound(e.getMessage());
        }
    }

    public JServer(int serverPort) throws IOException{
        this(serverPort, (ServeClient) null);
    }

    public JServer() throws IOException{
        this(DEFAULT_SERVER_PORT, (ServeClient) null);
    }

    public JServer(Class<? extends RequestManager> requestHandler) throws IOException{
        this(DEFAULT_SERVER_PORT, requestHandler);
    }

    public JServer(int serverPort, ServeClient serveClient) throws IOException{
        serverSocket = new ServerSocket(serverPort);
        this.serveClient = serveClient;
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

    public Class<? extends RequestManager> getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(Class<? extends RequestManager> requestHandler) {
        this.requestHandler = requestHandler;
    }

    public ServeClient getServeClient() {
        return serveClient;
    }

    public void setServeClient(ServeClient serveClient) {
        this.serveClient = serveClient;
    }

    public void start(){
        System.out.println("Server started on "+ getDefaultServerPort());
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
        if(serveClient!=null){
            new Thread(()->{
                try {
                    System.out.println("Socket accepted: "+socket);
                    serveClient.serve(socket);
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
