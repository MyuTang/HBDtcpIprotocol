package com.hbd.socket.server.parser;

import io.netty.buffer.ByteBuf;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

public class PacketTimeParser implements Parser<LocalDateTime, ByteBuf> {

    @Override
    public LocalDateTime parse(ByteBuf buf) {
        Objects.requireNonNull(buf);

        int year = buf.getShort(0);
        int month = buf.getShort(2);
        int day = buf.getShort(4);
        int hour = buf.getShort(6);
        int minute = buf.getShort(8);
        int second = buf.getShort(10);
        try {
            return LocalDateTime.of(year, month, day, hour, minute, second);
        } catch (DateTimeException e) {
            throw new ParserException(e);
        }
    }
}
