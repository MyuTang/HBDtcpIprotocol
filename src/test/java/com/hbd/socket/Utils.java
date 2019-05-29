package com.hbd.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.time.LocalDateTime;

public class Utils {
    private static String hexData = "07E20009001B000F002D00001980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000FFEEAA5507E20009001B000F002D00001980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000";
    private static ByteBuf buffer;

    static {
        buffer = Unpooled.buffer();
        for (int i = 0; i + 4 <= hexData.length(); i += 4) {
            buffer.writeShort(Integer.parseUnsignedInt(hexData.substring(i, i + 4), 16));
        }
    }

    public static ByteBuf getByteBuf() {
        return buffer.retain();
    }

    public static ByteBuf getPacket(LocalDateTime dateTime) {
        ByteBuf copy = buffer.copy();
        copy.setShort(0, dateTime.getYear());
        copy.setShort(2, dateTime.getMonthValue());
        copy.setShort(4, dateTime.getDayOfMonth());
        copy.setShort(6, dateTime.getHour());
        copy.setShort(8, dateTime.getMinute());
        copy.setShort(10, dateTime.getSecond());
        return copy;
    }
}
