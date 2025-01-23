import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

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
        ArrayList<String> items = new ArrayList<>();
        while (!Objects.equals(userInput, "bye")){
            if (Objects.equals(userInput, "list")){
                System.out.println("   ____________________________________________________________\n   ");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println("    " + Integer.toString(i + 1) + ". " + items.get(i));
                }
                System.out.println("   ____________________________________________________________\n");
            }
            else {
                items.add(userInput);
                System.out.println("   ____________________________________________________________\n   added: " +
                    userInput + "\n"
                    + "   ____________________________________________________________\n");
            }
            userInput = userScanner.nextLine();
        }
        System.out.println( "   ____________________________________________________________\n  " +
            " You're weak... the next time I see you, I'd win.\n"
            + "   ____________________________________________________________\n");
    }
}
