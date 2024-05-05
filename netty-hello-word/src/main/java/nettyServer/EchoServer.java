package nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * ProjectName:  Netty
 * PackageName:  PACKAGE_NAME
 * ClassName:    EchoServer
 *
 * @Author chnpngwng
 * @Date 2024 05 05 13 38
 **/
public class EchoServer {

    public static void main(String[] args) throws Exception {
        // 创建两个EventLoopGroup，用于处理客户端连接和网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建ServerBootstrap对象，用于配置服务器
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 使用NIO传输Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler()); // 添加EchoServerHandler处理器
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口并启动服务器
            ChannelFuture f = b.bind(8080).sync();
            System.out.println("Echo Server started on port 8080.");

            // 等待服务器Socket关闭
            f.channel().closeFuture().sync();
        } finally {
            // 释放资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

