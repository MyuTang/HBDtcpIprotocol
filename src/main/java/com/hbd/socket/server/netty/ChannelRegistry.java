/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hbd.socket.server.netty;

import io.netty.channel.Channel;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

/**
 * 通道库
 *
 * @author HJX
 * @date 2018/9/28
 * @copyright Hanboard
 * Created by HJX on 2018/9/17.
 */
public class ChannelRegistry {
    private final Deque<Channel> channelStack = new ConcurrentLinkedDeque<>();

    private ConcurrentMap<String, Channel> channelCache = new ConcurrentHashMap<>();

    public ChannelRegistry put(String key, Channel value) {
        channelCache.put(key, value);
        channelStack.push(value);
        return this;
    }

    public Channel get(String key) {
        return channelCache.get(key);
    }

    public void remove(String key) {
        this.channelCache.remove(key);
    }

    public int size() {
        return this.channelCache.size();
    }

    public Channel lastActiveChannel() {
        while (!channelStack.isEmpty() && !channelStack.peek().isActive()) {
            channelStack.pop();
        }

        if (channelStack.isEmpty()) {
            return null;
        } else {
            return channelStack.peek();
        }
    }

}
