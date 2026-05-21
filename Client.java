import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            System.out.println("Connected to server successfully!");

            String serverLine;
            while ((serverLine = in.readLine()) != null) {
                if (serverLine.equals("--- END_OF_MENU ---")) {
                    break;
                }
                System.out.println(serverLine);
            }

            System.out.println("\nEnter product IDs to add to your order. Type 'checkout' to get receipt and exit:");

            String msg;
            while (true) {
                msg = sc.nextLine().trim();

                if (msg.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    break;
                }

                out.println(msg);

                if (msg.equalsIgnoreCase("checkout")) {
                    while ((serverLine = in.readLine()) != null) {
                        if (serverLine.equals("--- END_OF_RECEIPT ---")) {
                            break;
                        }
                        System.out.println(serverLine);
                    }
                    break;
                }

                String response = in.readLine();
                if (response != null) {
                    System.out.println(response);
                }
            }

            socket.close();
            sc.close();
            System.out.println("Disconnected from server.");

        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
