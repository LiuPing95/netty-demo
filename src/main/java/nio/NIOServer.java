package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2020/1/10 4:04 PM
 **/
public class NIOServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.bind(new InetSocketAddress(8080));

        System.out.println("服务器启动...");

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            System.out.println("等待连接中...");
            if (selector.select(1000) <= 0) {
                System.out.println("1111111");
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            System.out.println("selectedKeys size:" + selectedKeys.size());
            System.out.println("selector.keys() size:" + selector.keys().size());

            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("连接成功...");
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    System.out.println(socketChannel.isConnected());
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("收到的消息：" + new String(byteBuffer.array()));
                }
                // 移除防止重复操作
                iterator.remove();
            }
        }
    }
}
