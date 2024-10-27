import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(br.readLine());
            System.out.println(br.readLine());

            
            String clientName = scanner.nextLine();
            pw.println(clientName);
            System.out.println(br.readLine());

            while (true) {
                System.out.print("Votre requête: ");
                String request = scanner.nextLine();
                pw.println(request);
                
                if (request.equalsIgnoreCase("exit")) {
                    System.out.println("Déconnexion du serveur...");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

