package com.hbd.socket.server.parser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.assertj.core.data.Index;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexParserTest {
    private final IndexParser parser = new IndexParser();
    private String hexData = "07E20009001B000F002D00001980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000FFEEAA5507E20009001B000F002D00001980000112568954000000000000000000000321000000000000000000C8012C00B400370078004B411CCCCD411B3333411B3333411CCCCD07E20009001B000F002D00000000411CCCCD411B3333411B33334100CCCD447A0666447A066600000000000000000000000000000000000000000000000000000000";
    @Test
    public void parse() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i + 4 <= hexData.length(); i += 4) {
            buffer.writeShort(Integer.parseUnsignedInt(hexData.substring(i, i + 4), 16));
        }
        ByteBuf dest = Unpooled.buffer();
        buffer.getBytes(6 * 2, dest, 4);
        int l = dest.readInt();
        int i = l & 0x7FFFF;
        int i1 = (l >> 19) & 0x03FF;
        Assert.assertEquals(String.format("%3d%06d", i1, i), parser.parse(buffer));
    }
}