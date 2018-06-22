package test.Netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public interface IBizHandle{
	ChannelFuture dealWith(ChannelHandlerContext ctx, Object msg);
}
