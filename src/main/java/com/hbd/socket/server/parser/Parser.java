package com.hbd.socket.server.parser;

public interface Parser<O, I> {
    O parse(I i);
}
