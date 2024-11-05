import java.io.IOException;

public class ChatApplication {

    public static void main(String[] args)
    {
        if(args.length < 1) {
            System.out.println("Usage: ClientApplication <server | client> [host] [port]");
            return;
        }
        String mode = args[0];
        int port = args.length> 2 ? Integer.parseInt(args[2]) : 12345;
        String host = args.length > 1 ? args[1] : "localhost";

        if("server".equalsIgnoreCase(mode))
            startServer(port);
        else if("client".equalsIgnoreCase(mode))
            startClient(host,port);
        else System.out.println("Unknown mode. Use 'server' or 'client' ");
    }

    private static void startServer(int port)
    {
        try {
            ChatServer server = new ChatServer();
            UserInterface ui = new UserInterface();

            server.startServer(port);
            System.out.println("Type your message and hit enter to send");

            new Thread(() -> {
                try {
                    String clientMessage;
                    while ((clientMessage = server.receiveMessage()) != null) {
                        ui.displayMessage("Client: " + clientMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed");
                }
            }).start();

            while(true) {
            String message = ui.getInput();
            server.sendMessage(message);
            }
        } catch(IOException e){
                System.out.println("Error starting server: " + e.getMessage());
            }
    }

    private static void startClient(String host, int port)
    {
        try {
            ChatClient client = new ChatClient();
            UserInterface ui = new UserInterface();

            client.connectToServer(host,port);
            System.out.println("Type your message and hit enter to send");

            new Thread(()-> {
                try{
                    String serverMessage;
                    while((serverMessage = client.receiveMessage()) !=null){
                        ui.displayMessage("Server: Message from client  "+ client  + " " + serverMessage);
                    }
                }catch (IOException e){
                    System.out.println("Connection closed");
                }
            }).start();

            while(true)
            {
                String message = ui.getInput();
                client.sendMessage(message);
            }
        }catch (IOException e)
        {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

}
