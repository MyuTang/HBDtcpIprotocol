package com.hbd.socket.server.netty.handler;

import com.hbd.socket.server.domain.ReplyMessage;
import com.hbd.socket.server.parser.PacketTimeParser;
import com.hbd.socket.server.parser.ParserException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
@Component
@Qualifier("timeSyncHandler")
public class TimeSyncHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TimeSyncHandler.class);
    private static final ZoneOffset DEFAULT_OFFSET = OffsetTime.now().getOffset();
    private PacketTimeParser parser = new PacketTimeParser();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("time sync handler channel read called with {}", msg);
        ByteBuf buf = (ByteBuf) msg;
        if (!validateTime(buf)) {
            //微震特殊情况 延迟五秒校验
            TimeUnit.SECONDS.sleep(5);
            logger.info("sync time {}", ctx.channel().attr(IndexExtractHandler.indexKey).get());
            ByteBuf syncCommand = ctx.channel().alloc().buffer();
            new ReplyMessage().write(syncCommand);
            ctx.channel().writeAndFlush(syncCommand);
        }
        super.channelRead(ctx, msg);
    }

    boolean validateTime(ByteBuf buf) {
        try {
            LocalDateTime packetTime = parser.parse(buf);

            Instant packetInstant = packetTime.toInstant(DEFAULT_OFFSET);
            Instant nowInstant = LocalDateTime.now().toInstant(DEFAULT_OFFSET);
            Duration between = Duration.between(packetInstant, nowInstant);

            return between.compareTo(Duration.ofMinutes(2)) < 0;
        } catch (ParserException e) {
            return false;
        }
    }
}
