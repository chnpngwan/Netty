package nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ProjectName:  Netty
 * PackageName:  PACKAGE_NAME
 * ClassName:    EchoClient
 *
 * @Author chnpngwng
 * @Date 2024 05 05 13 39
 **/

public class EchoClient {

    public static void main(String[] args) throws Exception {
        // 创建一个EventLoopGroup，用于处理客户端连接和网络读写
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建Bootstrap对象，用于配置客户端
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class) // 使用NIO传输Channel
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler()); // 添加EchoClientHandler处理器
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            // 连接到服务器，并启动客户端
            ChannelFuture f = b.connect("localhost", 8080).sync();

            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 释放资源
            group.shutdownGracefully();
        }
    }
}

