
import jsc.jServer.JServer;
import java.io.IOException;

public class EchoServer {
    public static void main(String []s){
        try{
            JServer echoServer = new JServer(EchoJRequestManagerImpl.class);
            echoServer.start();
        }catch (IOException e){
            System.out.println("Unable to start server :(");
        }
    }
}
