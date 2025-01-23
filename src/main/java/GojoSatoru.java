import java.util.Objects;
import java.util.Scanner;

public class GojoSatoru {
    public static void main(String[] args) {
        String introText = "   ____________________________________________________________\n" +
            "   Hello! I'm Gojo Satoru\n" +
            "   Am I the strongest chatbot because I'm Gojo Satoru\n" +
            "   or am I the Gojo Statoru because I am the weakest chatbot?\n" +
            "   What can I do for you?\n" + "   ____________________________________________________________\n";
        System.out.println(introText);
        Scanner userScanner = new Scanner(System.in);
        String userInput = userScanner.nextLine();
        while (!Objects.equals(userInput, "bye")){
            System.out.println("   ____________________________________________________________\n   " +
                userInput + "\n"
            + "   ____________________________________________________________\n");
            userInput = userScanner.nextLine();
        }
        System.out.println( "   ____________________________________________________________\n  " +
            " You're weak... the next time I see you, I'd win.\n"
            + "   ____________________________________________________________\n");
    }
}
