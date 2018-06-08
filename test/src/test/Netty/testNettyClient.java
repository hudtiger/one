package test.Netty;

import java.util.Arrays;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.serialization.ClassResolvers; 

public class testNettyClient {

	public static void runClient(ChannelHandler handler) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap().group(workerGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(
									new ObjectDecoder(1024,ClassResolvers.cacheDisabled(this.getClass().getClassLoader()))
									,new ObjectEncoder()
									,handler); //Method-Object:handle with POJO 
						}
					});

			// Start the client.
			ChannelFuture f = b.connect("localhost", 8899).sync(); // (5)

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		//启动10个客户端(貌似占用相同的消息通道)
//		for(int i=0;i<10;i++) {
//			runClient(new ClientConsumeHandler(null,null));
//		}
		
		runClient(new ClientConsumeHandler(()->Arrays.asList("1","2","3","4","5","6","7","8","9","Q"), res->System.out.println(res)));
		
		//消息重播
//		runClient(new ClientConsumeHandler(()->Arrays.asList("1","1","1","1"), res->System.out.println(res)));
	}
}


class ClientConsumeHandler extends ChannelInboundHandlerAdapter {
	
	final IBizRequest request;
	final IBizRequestCallback callback;
	public ClientConsumeHandler(IBizRequest request,IBizRequestCallback callback) {
		this.request = request;
		this.callback = callback;
	}

	@Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception { 
		ctx.writeAndFlush(ctx.channel().id()+" connectting");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { 
		if(this.callback!=null) {
	    	this.callback.completed(msg);
	    }
	    
	    if(this.request!=null) {
	    	this.request.run().forEach(req->{
	    		ctx.write(req);
	    	});
	    	ctx.writeAndFlush("Q"); //正常关闭
	    }
	    else {
	    	System.out.println(msg);
    		ctx.writeAndFlush("Empty Request"); 
	    	//ctx.writeAndFlush("Q").addListener(ChannelFutureListener.CLOSE); //做重播检测后不需要主动退出
	    }
	 //   Arrays.asList("1","2","3","4","5","6","7","8","9","Q").forEach(item->ctx.writeAndFlush(item));
	 //   ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
