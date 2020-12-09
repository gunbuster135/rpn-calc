package rpn;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        var input = new Scanner(System.in);
        var service = new RpnService();

        while (true) {
            String line = input.nextLine();
            if (line == null || line.isBlank()) {
                System.out.println("Empty line provided, please provide operands or operations!");
            }
            try {
                service.process(line);
            } catch (RpnProcessingException e) {
                //Consider more error friendly messages
                System.out.println(e.getMessage());
            }

            System.out.println("Stack: " + service.getStackOutput());
        }
    }

}
