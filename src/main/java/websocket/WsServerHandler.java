package websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles a server-side channel.
 */
public class WsServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
{

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        Channel currentChannel = ctx.channel();
//        ctx.fireChannelRead(currentChannel.remoteAddress() + " come in...");
        /*for (Channel channel : channelsList)
        {
            if (currentChannel != channel)
            {
                channel.writeAndFlush(currentChannel.remoteAddress() + " come in...");
            }
        }*/
//        currentChannel.writeAndFlush(currentChannel.remoteAddress() + " come in...");

        channelGroup.add(currentChannel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        Channel currentChannel = ctx.channel();
//        for (Channel channel : channelGroup)
//        {
//            if (currentChannel != channel)
//            {
//                channel.writeAndFlush(currentChannel.remoteAddress() + " leaved ...");
//            }
//        }
        channelGroup.remove(currentChannel);
    }

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        channelRead0(ctx, msg.toString());
        ctx.writeAndFlush(msg.toString());
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception
    {
        Channel       currentChannel = ctx.channel();
//        SocketAddress socketAddress  = currentChannel.remoteAddress();
//        System.out.println("receive \"" + msg.text() + "\" from " + socketAddress);
//        for (Channel channel : channelGroup)
//        {
//            if (currentChannel != channel)
//            {
//                channel.writeAndFlush(socketAddress + " send message : " + msg);
//            }
//            else
//            {
//                channel.writeAndFlush(new TextWebSocketFrame("you have send : " + msg.text()));
//            }
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel currentChannel = ctx.channel();
        System.out.println(currentChannel + " has broken...");
        channelGroup.remove(currentChannel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
//        Channel currentChannel = ctx.channel();
        if (channelGroup.size() % 100 == 0) {

            System.out.println(channelGroup.size());
        }

//        System.out.println(currentChannel.remoteAddress() + " is connecting...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Channel currentChannel = ctx.channel();
        System.out.println(currentChannel.remoteAddress() + " is disconnected...");
    }

}
