package com.hbd.socket.server.netty.handler;

import com.hbd.socket.server.netty.IndexRegistry;
import com.hbd.socket.server.parser.IndexParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class IndexExtractHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(IndexExtractHandler.class);

    public IndexExtractHandler(IndexRegistry indexRegistry) {
        this.indexRegistry = indexRegistry;
    }

    private final IndexRegistry indexRegistry;
    public static final String INDEX_EXTRACT_HANDLER = "IndexExtractHandler";
    public static final AttributeKey<String> indexKey = AttributeKey.newInstance("index");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<String> attrIndex = ctx.channel().attr(indexKey);
        if (attrIndex.get() == null) {
            String index = new IndexParser().parse(((ByteBuf) msg).duplicate().retain());
            attrIndex.set(index);
            logger.info("registry index with channel {} {}", attrIndex.get(), ctx.channel());
            indexRegistry.registery(index, ctx.channel());
        }

        super.channelRead(ctx, msg);
        ChannelHandler handler = ctx.pipeline().remove(IndexExtractHandler.class);
        ctx.pipeline().addLast(handler);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String index = ctx.channel().attr(indexKey).get();
        if (index != null && !index.equals("")) {
            indexRegistry.remove(index);
        }
        super.channelInactive(ctx);
    }
}
