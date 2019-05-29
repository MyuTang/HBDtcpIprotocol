package com.hbd.socket.server.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Ren Xunxiao
 * @date 2018/10/11
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/11
 */
public class ByteArrayToHexStringDecoder extends MessageToMessageDecoder<ByteBuf> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = msg.readableBytes();
        logger.debug("size {}", length);
        StringBuilder sb = new StringBuilder();
        while (msg.isReadable()) {
            String hex = String.format("%02X", msg.readByte());
            sb.append(hex);
        }
        out.add(sb.toString());
    }
}
