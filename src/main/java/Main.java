import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        ExecutorService ex = Executors.newFixedThreadPool(4);

        int j = 0;

//        Client client = new Client("Client");
//        while (j < 2) {
//            j++;
////            ex.execute(new Client("Client " + j));
//            ex.execute(new Thread(new Client("Client" + j)));
//            Thread.sleep(100);
//        }
//        ex.shutdown();
        Client client = new Client("Client-2");
        new Thread(client).start();
    }
}
