import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedChatServer {

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public void startServer(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler= new ClientHandler(clientSocket,this);
                clients.add(clientHandler);

                new Thread(clientHandler).start();
            }
        }catch (IOException e){
            System.out.println("Error starting server " + e.getMessage());
        }

    }

    public synchronized void broadcastMessage(String message, ClientHandler sender)
    {
        System.out.println("Broadcasting message " + message);
        for(ClientHandler client : clients)
        {
            if(client!=sender)
                client.sendMessage(message);
        }
    }

    public synchronized void removeClient(ClientHandler client)
    {
        clients.remove(client);
    }

    public static void main(String[] args)
    {
        int port = 12345;
        MultiThreadedChatServer server = new MultiThreadedChatServer();
        server.startServer(port);
    }

}
