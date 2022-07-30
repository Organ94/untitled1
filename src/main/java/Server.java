import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {

    static final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(PORT);

        Semaphore semaphore = new Semaphore(2);

        while (true) {
            semaphore.acquire();
            Socket socket = server.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(in.readLine());

//            new Thread(() -> {
//
//                try (Socket socket1 = socket){
//                    serve(socket1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
            executorService.execute(() -> {
                try (Socket socket1 = socket) {
                    serve(socket1);
                    out.println(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });

        }
    }

    private static void serve(final Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        if (socket.isConnected()) {
            out.println("Socket connected...\"" +
                    socket.getLocalAddress().getHostAddress() + ":" +
                    socket.getLocalPort() + "\"");
        }
        String msg;

        while ((msg = in.readLine()) != null) {
            if (msg.equalsIgnoreCase("end")) {
                out.println("Socket disconnecting...");
                socket.close();
                break;
            }
//            executorService.execute(new ThreadClientDialog(socket));
            out.println(Thread.currentThread().getName() + ":" + msg);
        }
    }
}
