package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/12/5 8:31 PM
 **/
public class ChatClientInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
//        pipeline.addLast("frame", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("handler", new ChatClientHandler());
    }
}
