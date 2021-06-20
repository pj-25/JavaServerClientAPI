
import jsc.jServer.JServer;
import java.io.IOException;

public class EchoServer {
    public static void main(String []s){
        try{
            JServer pingServer = new JServer(EchoJRequestManagerImpl.class);
            pingServer.start();
        }catch (IOException e){
            System.out.println("Unable to start server :(");
        }
    }
}
