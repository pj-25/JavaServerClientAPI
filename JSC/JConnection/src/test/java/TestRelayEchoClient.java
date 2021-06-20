import jsc.jConnection.JConnection;

import java.io.IOException;
import java.util.Scanner;

public class TestRelayEchoClient {

    static public Scanner scanner = new Scanner(System.in);
    public static void main(String []s){
        try{
            JConnection jConnection = new JConnection("127.0.0.1", 5656, (res)->{
                //write your logic here to consume the response
                System.out.println(res);
            });
            jConnection.run();
            System.out.println("Write your message and press enter...(write \"exit\" to stop)");
            String msg;
            while(jConnection.isConnected()){
                msg = scanner.nextLine();
                if(msg.equals("exit")){
                    break;
                }
                jConnection.write(msg);
            }
            jConnection.close();
            System.out.println("----------- Bye :) -----------");
        }catch (IOException ioE){
            System.out.println("Server connection lost, check your network connection :(");
        }
    }

}
