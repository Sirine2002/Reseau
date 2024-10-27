import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int nombreClient;

    public static void main(String[] args) {
        new Server().start();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Démarrage du serveur...");
            while (true) {
                Socket socket = serverSocket.accept();
                ++nombreClient;
                new Conversation(socket, nombreClient).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Conversation extends Thread {
        private Socket socket;
        private int numeroClient;
        private String clientName;

        public Conversation(Socket socket, int numeroClient) {
            this.socket = socket;
            this.numeroClient = numeroClient;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                String IP = socket.getRemoteSocketAddress().toString();

                System.out.println("Connexion du client numéro " + numeroClient + " IP= " + IP);
                pw.println("Bienvenue! Vous êtes le client numéro: " + numeroClient);
                pw.println("Veuillez envoyer votre nom:");

                clientName = br.readLine();
                System.out.println("Le nom du client " + numeroClient + " est: " + clientName);
                pw.println("Bonjour " + clientName + "! Vous pouvez maintenant envoyer une requête.");

                while (true) {
                    String req = br.readLine();
                    if (req == null) {
                        System.out.println(clientName + " (" + IP + ") s'est déconnecté.");
                        break;
                    }
                    System.out.println(clientName + " (" + IP + ") a envoyé : " + req);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

