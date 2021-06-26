# JavaServerClientAPI

![COMMIT_STATUS](https://img.shields.io/github/last-commit/pj-25/JavaServerClientAPI?style=for-the-badge)

[Jump to JavaDocs --->](https://pj-25.github.io/JavaServerClientAPI/docs)

## JSC Architecture
* ### System Design
    ![JSC system architecture](https://github.com/pj-25/JavaServerClientAPI/blob/main/res/JSC-JSC_System_Architecture.png)
* ### JSC Integration
    ![JSC system integration](https://github.com/pj-25/JavaServerClientAPI/blob/main/res/JSC-JSC_Integration_System_Design.png)

## Library Structure

```javascript
jsc
├── jConnection
│   ├── JCloseEventConsumer.java
│   ├── JConnection.java
│   └── JConnectionManager.java
├── jEventManager
│   ├── JEventCode.java
│   ├── JEventConsumer.java
│   ├── JEventDataSender.java
│   ├── JEventManager.java
│   └── JEventType.java
├── jMessageHandler
│   ├── JFileConsumer.java
│   ├── JMessageCode.java
│   ├── JMessageConsumer.java
│   ├── JMessageDelimiter.java
│   ├── JMessageFormatHandler.java
│   ├── JMessageSender.java
│   └── JMessageType.java
├── jObjectParser
│   ├── JObjectParseException.java
│   └── JObjectParser.java
└── jServer
    ├── JClientConnectionsHandler.java
    ├── JConnectionHandler.java
    ├── JRequestManager.java
    ├── JResponseSender.java
    ├── JServer.java
    └── JSocketConsumer.java

5 directories, 23 files
```

## Getting Started 

* ### Server Side:
    * #### Server implementation:
    
        * ```EchoServer.java```
            ```java
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
            ```
        * ```EchoJRequestManagerImpl.java```
            ```java
            import jsc.jServer.JRequestManager;

            public class EchoJRequestManagerImpl extends JRequestManager {
                @Override
                public void accept(String req){    
                    //write your logic here to serve the request
                    
                    System.out.println("Received Request: "+req);   
                    write(req);
                }
            }
            ```
        * Default server port: ```5656```
    
    * #### Run Server:
        ```bash
        javac -cp JSERVER.jar EchoJRequestManagerImpl.java
        javac -cp JSERVER.jar:. EchoServer.java
        java -cp JSERVER.jar:. EchoServer
        ```
* ### Client Side: 
    * #### Client implementation:
        * ```TestRelayEchoClient.java```
            ```java
            import jsc.jConnection.JConnection;

            import java.io.IOException;
            import java.util.Scanner;

            public class TestRelayEchoClient {

                public static Scanner scanner = new Scanner(System.in);
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
            ```
        * Output:
            ```
            Write your message and press enter...(write "exit" to stop)
            Hello, Server!
            Hello, Server!
            JSC is perfectly working :)
            JSC is perfectly working :)
            exit
            ----------- Bye :) -----------
            anonymous disconnected!
            ```
            
## Proof of Concept: 

1. [Gameholic](https://github.com/pj-25/Gameholic)

## Project Structure

```javascript
JSC
├── JConnection
│   ├── JConnection.iml
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── jsc
│   │   │   │       ├── jConnection
│   │   │   │       │   ├── JCloseEventConsumer.java
│   │   │   │       │   ├── JConnection.java
│   │   │   │       │   └── JConnectionManager.java
│   │   │   │       ├── jEventManager
│   │   │   │       │   ├── JEventCode.java
│   │   │   │       │   ├── JEventConsumer.java
│   │   │   │       │   ├── JEventDataSender.java
│   │   │   │       │   ├── JEventManager.java
│   │   │   │       │   └── JEventType.java
│   │   │   │       ├── jMessageHandler
│   │   │   │       │   ├── JFileConsumer.java
│   │   │   │       │   ├── JMessageCode.java
│   │   │   │       │   ├── JMessageConsumer.java
│   │   │   │       │   ├── JMessageDelimiter.java
│   │   │   │       │   ├── JMessageFormatHandler.java
│   │   │   │       │   ├── JMessageSender.java
│   │   │   │       │   └── JMessageType.java
│   │   │   │       └── jObjectParser
│   │   │   │           ├── JObjectParseException.java
│   │   │   │           └── JObjectParser.java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   │           └── jsc
│   │               └── jConnection
│   │                   └── JConnectionTest.java
│   └── target
│       ├── classes
│       │   └── jsc
│       │       ├── jConnection
│       │       │   ├── JCloseEventConsumer.class
│       │       │   ├── JConnection.class
│       │       │   └── JConnectionManager.class
│       │       ├── jEventManager
│       │       │   ├── JEventCode.class
│       │       │   ├── JEventConsumer.class
│       │       │   ├── JEventDataSender.class
│       │       │   ├── JEventManager.class
│       │       │   └── JEventType.class
│       │       ├── jMessageHandler
│       │       │   ├── JFileConsumer.class
│       │       │   ├── JMessageCode.class
│       │       │   ├── JMessageConsumer.class
│       │       │   ├── JMessageDelimiter.class
│       │       │   ├── JMessageFormatHandler.class
│       │       │   ├── JMessageSender.class
│       │       │   └── JMessageType.class
│       │       └── jObjectParser
│       │           ├── JObjectParseException.class
│       │           └── JObjectParser.class
│       ├── generated-sources
│       │   └── annotations
│       ├── generated-test-sources
│       │   └── test-annotations
│       └── test-classes
│           └── jsc
│               └── jConnection
│                   └── JConnectionTest.class
├── JSERVER
│   ├── JSERVER.iml
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── jsc
│   │   │   │       └── jServer
│   │   │   │           ├── JClientConnectionsHandler.java
│   │   │   │           ├── JConnectionHandler.java
│   │   │   │           ├── JRequestManager.java
│   │   │   │           ├── JResponseSender.java
│   │   │   │           ├── JServer.java
│   │   │   │           └── JSocketConsumer.java
│   │   │   └── resources
│   │   └── test
│   │       └── java
│   │           ├── EchoJRequestManagerImpl.java
│   │           └── EchoServer.java
│   └── target
│       ├── classes
│       │   └── jsc
│       │       └── jServer
│       │           ├── JClientConnectionsHandler.class
│       │           ├── JConnectionHandler.class
│       │           ├── JRequestManager.class
│       │           ├── JResponseSender.class
│       │           ├── JServer$RequestHandlerNotFound.class
│       │           ├── JServer.class
│       │           └── JSocketConsumer.class
│       ├── generated-sources
│       │   └── annotations
│       ├── generated-test-sources
│       │   └── test-annotations
│       └── test-classes
│           ├── EchoJRequestManagerImpl.class
│           └── EchoServer.class
└── libs
    ├── JConnection.jar
    └── JSERVER.jar

47 directories, 59 files
```
