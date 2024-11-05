import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private MultiThreadedChatServer server;

    public ClientHandler(Socket socket, MultiThreadedChatServer server)
    {
        this.socket = socket;
        this.server = server;
        try{
            this.out = new PrintWriter(socket.getOutputStream(),true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error setting up client handler streams: " + e.getMessage());
        }
    }


    @Override
    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while((message = in.readLine())!=null) {
                System.out.println("Received: " + message);
                server.broadcastMessage(message,this);
            }
        }catch (IOException e){
            System.out.println("Client disconnected " + e.getMessage());
        } finally {
            server.removeClient(this);
            try{
                socket.close();
            }catch (IOException e)
            {
                System.out.println("Error closing socket " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message)
    {
        out.println(message);
        System.out.println("Sent to client: " + message);
    }

}
