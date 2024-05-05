package nettyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ProjectName:  Netty
 * PackageName:  PACKAGE_NAME
 * ClassName:    EchoServerHandler
 *
 * @Author chnpngwng
 * @Date 2024 05 05 13 39
 **/

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 将接收到的消息写回到客户端
        ctx.write(msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 发生异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}

