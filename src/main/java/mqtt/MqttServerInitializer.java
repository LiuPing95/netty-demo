package mqtt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author liu ping
 * @Version 1.x.0
 * @Description
 * @Date 2019/12/5 8:26 PM
 **/
public class MqttServerInitializer extends ChannelInitializer<SocketChannel>
{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
//        System.out.println("客户端连接：" + socketChannel.remoteAddress());
        //用户定义的ChannelInitailizer加入到这个channel的pipeline上面去，这个handler就可以用于处理当前这个channel上面的一些事件
        ChannelPipeline pipeline = socketChannel.pipeline();
        //ChannelPipeline类似于一个管道，管道中存放的是一系列对读取数据进行业务操作的ChannelHandler。

        /**
         * 发送的数据在管道里是无缝流动的，在数据量很大时，为了分割数据，采用以下几种方法
         * 定长方法
         * 固定分隔符
         * 将消息分成消息体和消息头，在消息头中用一个数组说明消息体的长度
         */
        pipeline.addLast("JymHttpSeverCodec",new HttpServerCodec());
        pipeline.addLast("JymChunkedWriteHandler",new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
//        pipeline.addLast(new Mqtt("/hello"));
        pipeline.addLast("handler", new MqttServerHandler());
    }
}
