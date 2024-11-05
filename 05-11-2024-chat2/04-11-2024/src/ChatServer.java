import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Server Started. Waiting for a client to connect...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }

    public void sendMessage(String message)
    {
        out.println(message);
    }

    public void close() throws IOException
    {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public String receiveMessage() throws IOException
    {
        return in.readLine();
    }
}
