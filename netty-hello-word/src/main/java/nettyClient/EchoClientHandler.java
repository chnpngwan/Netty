package nettyClient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ProjectName:  Netty
 * PackageName:  PACKAGE_NAME
 * ClassName:    EchoClientHandler
 *
 * @Author chnpngwng
 * @Date 2024 05 05 13 40
 **/

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当连接建立时发送消息到服务器
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes("Hello, Netty!".getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 接收到服务器的响应消息并打印
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("Server response: " + new String(bytes));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 发生异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}

