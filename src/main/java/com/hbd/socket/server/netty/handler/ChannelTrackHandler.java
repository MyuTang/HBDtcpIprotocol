package com.hbd.socket.server.netty.handler;

import com.hbd.socket.server.netty.ChannelRegistry;
import com.hbd.socket.server.parser.IndexParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Ren Xunxiao
 * @date 2018/10/22
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/22
 */
@ChannelHandler.Sharable
@Component
@Qualifier("channelTrack")
public class ChannelTrackHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ChannelRegistry channelRegistry;

    public ChannelTrackHandler(ChannelRegistry channelRegistry) {
        this.channelRegistry = channelRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Objects.requireNonNull(channelRegistry);
        String remoteAddress = ctx.channel().remoteAddress().toString();
        channelRegistry.put(remoteAddress, ctx.channel());
        logCurrentActiveChannel();
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

    private void logCurrentActiveChannel() {
        logger.info("current active channel: {}", channelRegistry.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Objects.requireNonNull(channelRegistry);
        String remoteAddress = ctx.channel().remoteAddress().toString();
        channelRegistry.remove(remoteAddress);
        logCurrentActiveChannel();
        ctx.fireChannelInactive();
    }
}
