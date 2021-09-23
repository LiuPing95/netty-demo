package nio.chat;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2020/1/12 11:32 PM
 **/
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8081);
        try {

            new Thread(() ->
            {

                while (true) {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        byte[] b = new byte[1024];
                        inputStream.read(b);
                        System.out.println(new String(b));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                OutputStream outputStream = socket.getOutputStream();
                byte[] b = msg.getBytes();
                outputStream.write(b);
                outputStream.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
