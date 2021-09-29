package netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/11/30 12:30 AM
 **/
public class ChatClientHandler extends SimpleChannelInboundHandler<String>
{

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        Channel currentChannel = ctx.channel();
        currentChannel.writeAndFlush("连接服务器成功");

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        System.out.println("收到消息：" + msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        channelRead0(ctx, msg.toString());
    }

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel currentChannel = ctx.channel();
        currentChannel.writeAndFlush("连接服务器成功");
    }*/
}

