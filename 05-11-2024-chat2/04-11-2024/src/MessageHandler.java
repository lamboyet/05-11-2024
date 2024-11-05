import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageHandler {
    private BufferedReader in;
    private PrintWriter out;

    public MessageHandler(BufferedReader in, PrintWriter out)
    {
        this.in=in;
        this.out=out;
    }

    public void readMessage()
    {
        new Thread(()->{String message;
        try{
            while((message = in.readLine()) != null) {
                System.out.println("Receiver: " + message);
            }
        }catch(IOException e){
            System.out.println("Connection closed. ");
        }
        }).start();
    }

    public void writeMessage(String message)
    {
        out.println(message);
    }
}
