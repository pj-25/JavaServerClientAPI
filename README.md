# JavaServerClientAPI

![COMMIT_STATUS](https://img.shields.io/github/last-commit/pj-25/JavaServerClientAPI?style=for-the-badge)

[Jump to JavaDocs --->](https://pj-25.github.io/JavaServerClientAPI/docs)

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
    * Server implementation:
    
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
                public void accept(String msg){
                    System.out.println("Received Request: "+msg);
                    write(msg);
                }
            }
            ```
    * Run Server:
        ```bash
        javac -cp JSERVER.jar EchoJRequestManagerImpl.java
        javac -cp JSERVER.jar:. EchoServer.java
        java -cp JSERVER.jar:. EchoServer
        ```

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
