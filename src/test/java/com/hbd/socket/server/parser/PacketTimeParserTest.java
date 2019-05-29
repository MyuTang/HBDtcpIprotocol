package com.hbd.socket.server.parser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

public class PacketTimeParserTest {
    private String hexData = "07E20009001B000F002D00101980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000FFEEAA5507E20009001B000F002D00001980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000";
    private ByteBuf buffer;

    {
        buffer = Unpooled.buffer();
        for (int i = 0; i + 4 <= hexData.length(); i += 4) {
            buffer.writeShort(Integer.parseUnsignedInt(hexData.substring(i, i + 4), 16));
        }
    }

    @Test
    public void parse() {
        System.out.println(new PacketTimeParser().parse(buffer));
    }

    @Test
    public void invalidateDateTime() {
        ByteBuf copy = buffer.copy();
        copy.setShort(10, 0xFF);
        try {
            new PacketTimeParser().parse(copy);
            Assert.fail();
        } catch (ParserException ignore) {

        } finally {
            copy.release();
        }
    }

}