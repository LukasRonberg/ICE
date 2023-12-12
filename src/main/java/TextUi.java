import java.util.Scanner;

public class TextUi {
    private Scanner scan = new Scanner(System.in);

    /**
     * shows a message and returns the user's input as a String
     * @param msg
     * @return
     */
    public String getInput(String msg) {
        this.displayMessage(msg);
        return scan.nextLine();
    }

    /**
     * displays a message
     * @param msg
     */
    public void displayMessage(String msg) {

        System.out.println(msg);

    }
    public void displayErrorMessage(String msg) {
        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";
        System.out.println(RED + msg + RESET);
    }
}

