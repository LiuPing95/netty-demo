package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/11/30 12:16 AM
 **/
public class ChatServer
{

    private int port;

    public ChatServer(int port)
    {
        this.port = port;
    }

    public void run() throws Exception
    {
        EventLoopGroup bossGroup   = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChatServerInitializer())
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = b.bind(port).sync();

            // Wait until the netty.server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your netty.server.
            System.out.println("netty.server is started");
            channelFuture.channel().closeFuture().sync();
            System.out.println("netty.server is stop");
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception
    {
        int port = 8080;
        if (args.length > 0)
        {
            port = Integer.parseInt(args[0]);
        }

        new ChatServer(port).run();
    }
}
