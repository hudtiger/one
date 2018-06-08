package test.Netty;

import java.util.HashMap;
import java.util.Map;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import test.Netty.UnixTime;

public class testNettyServer {
	public static void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
            		.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)
            		.handler(new LoggingHandler(LogLevel.INFO))
            		.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(
									//添加对象解码器 负责对序列化POJO对象进行解码 设置对象序列化最大长度为1M 防止内存溢出
									//设置线程安全的WeakReferenceMap对类加载器进行缓存 支持多线程并发访问  防止内存溢出
									new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader()))  
									,new ObjectEncoder()   //添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码  
									,new BizHandler((ctx,msg)-> ctx.write(msg)));
								//	,new ServerHandler<>(new UnixTime()));
						}
		             })
            		.option(ChannelOption.SO_BACKLOG, 128)          // (5)
            		.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            ChannelFuture f = b.bind(8899).sync(); // (7)绑定端口 同步等待绑定成功  
            f.channel().closeFuture().sync(); //等到服务端监听端口关闭
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//释放线程资源  
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

	public static void main(String[] args) throws Exception {
		run();
	}
}

class BizHandler extends ChannelInboundHandlerAdapter {
	private static IBizHandle exit = null;
	IBizHandle bizHandle;
	int counter = 0;
	int replayCounter = 0;
	
	static Map<String,Object> bizPool = null;
	static {
		if(exit==null) {
			exit = (ctx,msg)->{
				return ctx.writeAndFlush("[Response Completed & Server disconnected]").addListener(ChannelFutureListener.CLOSE); //正常退出关闭连接
			};
		}
		if(bizPool==null) {
			bizPool = new HashMap<>();
			bizPool.put("1", new UnixTime());
			bizPool.put("2", new UnixTime());
			bizPool.put("3", new UnixTime());
			bizPool.put("4", new UnixTime());
			bizPool.put("5", new UnixTime());
			bizPool.put("6", new UnixTime());
			bizPool.put("7", new UnixTime());
			bizPool.put("8", new UnixTime());
			bizPool.put("9", new UnixTime());
			bizPool.put("Q", BizHandler.exit);
		}
	}
	public BizHandler() {
		
	}
	public BizHandler(IBizHandle bizHandle) {
		this.bizHandle = bizHandle;
	}

	@Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
    	ctx.writeAndFlush(ctx.channel()+" prepared");
    }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    //重播检测
	    if(ReplayDefender.checkAllow(ctx.channel(), msg)) {
	    	System.err.printf("[Request-%s]:%s\r\n",counter++,msg.toString());
		    String reqCode = msg.toString();
		    if("Q".equalsIgnoreCase(reqCode)) //正常退出
		    	((IBizHandle)bizPool.get(reqCode)).dealWith(ctx,msg);
		    else if(this.bizHandle!=null&&bizPool.get(reqCode)!=null) {
		    	this.bizHandle.dealWith(ctx,String.format("[Response from %s]:%s %s",ctx.channel().id(),reqCode,bizPool.get(reqCode)));
		    }else {
		    	if("Empty Request".equals(msg)) {
		    		ctx.write("How dare you send a empty-request?");
		    		exit.dealWith(ctx, msg);
		    	}
		    	else
		    		ctx.writeAndFlush(msg);
		    }
	    }else {
	    	ctx.writeAndFlush("Replay attact is not allowed.").addListener(future->{
	    		System.out.printf("Replay occured & Server-%s is closed\r\n", ctx.channel());
	    		ctx.close();});
	    }
	}
	
	@Override 
	public void channelReadComplete(ChannelHandlerContext ctx) {
	//	System.out.printf("%s is closed\r\n", ctx.channel());
		//ctx.flush();
	}
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}