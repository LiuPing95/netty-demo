package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2020/1/10 5:04 PM
 **/
public class NIOClient
{

    public static void main(String[] args) throws Exception
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        if (!socketChannel.connect(new InetSocketAddress(8080)))
        {
            if (!socketChannel.finishConnect())
            {
                System.out.println("正在连接服务器...");
            }
        }

        String msg = "一条测试信息";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
