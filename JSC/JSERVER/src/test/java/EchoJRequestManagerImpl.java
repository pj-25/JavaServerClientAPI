import jsc.jServer.JRequestManager;

public class EchoJRequestManagerImpl extends JRequestManager {
    @Override
    public void accept(String msg){
        System.out.println("Received Request: "+msg);
        write(msg);
    }
}
