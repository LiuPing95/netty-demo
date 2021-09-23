package websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/11/30 12:16 AM
 **/
public class WsServer
{

    private int port;

    public WsServer(int port)
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
//                    .handler(new LoggingHandler(LogLevel.INFO))
             .channel(NioServerSocketChannel.class)
             .childHandler(new WsServerInitializer())
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            System.out.println("server is started");
            channelFuture.channel().closeFuture().sync();
            System.out.println("server is stop");
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

        new WsServer(port).run();
    }
}
