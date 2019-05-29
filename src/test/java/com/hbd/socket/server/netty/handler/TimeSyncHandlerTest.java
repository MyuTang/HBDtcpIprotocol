package com.hbd.socket.server.netty.handler;

import com.hbd.socket.Utils;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeSyncHandlerTest {
    private final TimeSyncHandler timeSyncHandler = new TimeSyncHandler();

    @Test
    public void channelRead() {

    }

    @Test
    public void invalidateTime() {
        boolean b = timeSyncHandler.validateTime(Utils.getPacket(LocalDateTime.now()));
        assertTrue(b);
        b = timeSyncHandler.validateTime(Utils.getPacket(LocalDateTime.now().minusMinutes(1)));
        assertTrue(b);
        b = timeSyncHandler.validateTime(Utils.getPacket(LocalDateTime.now().minusMinutes(3)));
        assertFalse(b);
    }

    @Test
    public void diff() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMinutesBefore = now.minusMinutes(3);
        ZoneOffset offset = OffsetTime.now().getOffset();
        Instant ni = now.toInstant(offset);
        Instant tmb = threeMinutesBefore.toInstant(offset);
        System.out.println(Duration.between(ni, tmb));
        System.out.println(Duration.between(tmb, ni));
        System.out.println(Duration.between(tmb, ni).compareTo(Duration.ofMinutes(2)));
    }
}