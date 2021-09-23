package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2020/1/10 7:43 PM
 **/
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 8081;

    public GroupChatServer() throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws Exception {
        while (true) {
            int count = selector.select();
            if (count > 0) {
                // 发生的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "上线...");
                    }
                    if (key.isReadable()) {
                        readData(key);
                    }
                    // 把处理的事件移除掉，避免重复处理
                    iterator.remove();
                }
            }
        }
    }

    public void readData(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int count = 0;
        try {
            count = socketChannel.read(byteBuffer);
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离开了");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (count > 0) {
            String msg = new String(byteBuffer.array());
            System.out.println("收到消息：" + msg);
            try {
                transfer(msg, socketChannel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void transfer(String msg, SocketChannel socketChannel) {
        System.out.println("服务器转发消息中...");
        Iterator<SelectionKey> iterator = selector.keys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            SelectableChannel channel = key.channel();

            if (channel instanceof SocketChannel/* && channel != socketChannel*/) {
                try {
                    ((SocketChannel) channel).write(ByteBuffer.wrap(msg.getBytes()));
                } catch (IOException e) {
                    try {
                        System.out.println(((SocketChannel) channel).getRemoteAddress() + "离开了");
                        channel.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    /*try
                    {
                        iterator.remove();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }*/
                }
            }
        }
        /*for (SelectionKey key : selector.keys())
        {

        }*/
    }

    public static void main(String[] args) throws Exception {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }


    class Player {

    }
}
