import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void startClient(String ipAddress, int port)
    {
        try{
            socket = new Socket(ipAddress, port);
            out = new PrintWriter(socket.getOutputStream(),true);
            in= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new IncomingMessageHandler(in)).start();

            Scanner scanner = new Scanner(System.in);
            String message;
            while(true)
            {
                System.out.println("You: ");
                message = scanner.nextLine();
                out.println(message);
            }
        }catch (IOException e)
        {
            System.out.println("Error connecting to server " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        String ipAddress = "localhost";
        int port = 12345;

        ChatClient client = new ChatClient();
        client.startClient(ipAddress,port);
    }

    class IncomingMessageHandler implements Runnable{
        private BufferedReader in;

        public IncomingMessageHandler(BufferedReader in)
        {
            this.in= in;
        }

        @Override
        public void run() {
            String message;
            try{
                while((message=in.readLine())!=null)
                {
                    System.out.println("\n Server: " + message);
                }
            }catch (IOException e){
                System.out.println("Disconnected from server ");
            }
        }
    }

    public void connectToServer(String ipAddress, int port) throws IOException
    {
        socket = new Socket(ipAddress,port);
        System.out.println("Connected to server!");

        out = new PrintWriter(socket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    public void sendMessage(String message)
    {
        out.println(message);
    }

    public String receiveMessage() throws IOException
    {
        return in.readLine();
    }

    public void closeClient() throws IOException
    {
        in.close();
        out.close();
        socket.close();
    }
}
