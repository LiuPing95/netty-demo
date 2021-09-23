package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/11/30 12:26 AM
 **/
public final class ChatClient {

//    static final String HOST = System.getProperty("host", "39.108.59.255");
    static final String HOST = System.getProperty("host", "localhost");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChatClientInitializer());
            ChannelFuture channelFuture = b.connect(HOST, PORT).sync();
            Channel channel = channelFuture.channel();
            System.out.println("1111111");
            //获取channel
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String str = scanner.nextLine();
                channel.writeAndFlush(str+"\n");
            }
            channel.closeFuture().sync();
            scanner.close();
        } finally {
            group.shutdownGracefully();
        }
    }
}
