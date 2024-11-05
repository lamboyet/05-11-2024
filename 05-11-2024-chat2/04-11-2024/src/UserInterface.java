import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface()
    {
        this.scanner = new Scanner(System.in);

    }

    public String getInput()
    {
        System.out.println("You: ");
        return scanner.nextLine();
    }

    public void displayMessage(String message)
    {
        System.out.println(message);
    }

    public void close()
    {
        scanner.close();
    }

}
