package com.hbd.socket.server.parser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexParser implements Parser<String, ByteBuf> {

    private static final int NUMBER_MASK = 0b1111111111111111111;
    private static final int LOCATION_MASK = 0b1111111111;

    private static final Logger logger = LoggerFactory.getLogger(IndexParser.class);

    @Override
    public String parse(ByteBuf buf) {
        ByteBuf indexBuf = buf.alloc().buffer();
        buf.getBytes(6 * 2, indexBuf, 4);

        int indexField = indexBuf.readInt();

        int number = indexField & NUMBER_MASK;
        int loc = (indexField >> 19) & LOCATION_MASK;

        String index = String.format("%03d%06d", loc, number);
        logger.debug("parse {} {}", ByteBufUtil.hexDump(indexBuf), index);
        indexBuf.release();
        return index;
    }
}
