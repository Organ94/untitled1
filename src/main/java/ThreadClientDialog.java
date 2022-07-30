import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadClientDialog implements Runnable {

    private static Socket clientDialog;

    public ThreadClientDialog(Socket client) {
        ThreadClientDialog.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");

                String msg = in.readUTF();

                if (msg.equalsIgnoreCase("end")) {
                    System.out.println("Client disconnecting...");
//                    clientDialog.close();
                    break;
                }

                out.writeUTF("Message from client: " + msg);
                out.flush();
            }

            System.out.println("Client disconnected!");
            System.out.println("Closing connections & channels...");

            in.close();
            out.close();

            clientDialog.close();

            System.out.println("Closing connections & channels - DONE");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
