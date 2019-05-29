package com.hbd.socket.server.netty;

import com.hbd.socket.server.netty.handler.IndexExtractHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class IndexRegistry {
    private final ConcurrentMap<String, Channel> indexChannel = new ConcurrentHashMap<>();

    public Channel registery(String index, Channel channel) {
        indexChannel.put(index, channel);
        return channel;
    }

    public void remove(String index) {
        indexChannel.remove(index);
    }

    public void remove(Channel channel) {
        String index = channel.attr(IndexExtractHandler.indexKey).get();
        remove(index);
    }

    public Optional<Channel> find(String index) {
        return Optional.ofNullable(indexChannel.get(index));
    }

    public Set<String> registeredIndexs() {
        return indexChannel.keySet();
    }
}
