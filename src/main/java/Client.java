import javax.swing.plaf.basic.BasicButtonUI;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

    static Socket socket;
    static String name;

    public Client(String name) {
        Client.name = name;
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Client connected to socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in)) {

            while (!socket.isClosed()) {

                String msg;
                System.out.println(in.readLine());
                Thread.sleep(1000);

                System.out.print("Введите сообщение или end для выхода\n>> ");

                msg = scanner.nextLine();

                if (msg.equalsIgnoreCase("end")) {
                    System.out.println("Socket closing...");
                    socket.close();
                    break;
                }
                out.println(Client.name + ":" + msg);
                out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
