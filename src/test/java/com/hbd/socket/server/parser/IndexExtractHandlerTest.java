package com.hbd.socket.server.parser;

import com.hbd.socket.Utils;
import com.hbd.socket.server.netty.IndexRegistry;
import com.hbd.socket.server.netty.handler.IndexExtractHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Assert;
import org.junit.Test;

public class IndexExtractHandlerTest {
    @Test
    public void test() {
        IndexExtractHandler handler = new IndexExtractHandler(new IndexRegistry());
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(handler);
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new LoggingHandler());
            }
        });

        System.out.println(channel.pipeline().names());
        Assert.assertSame(channel.pipeline().first(), handler);
        channel.writeInbound(Utils.getByteBuf());

        Assert.assertSame(channel.pipeline().last(), handler);

    }
}
