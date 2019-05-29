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
package com.hbd.socket.server.netty.handler;

import com.hbd.socket.server.DataPacket;
import com.hbd.socket.server.ThingClient;
import com.hbd.socket.server.netty.ChannelRegistry;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 处理接收到的数据
 *
 * @author HJX
 * @date 2018/9/28
 * @copyright Hanboard
 * Created by HJX on 2018/9/17.
 */
@Component
@ChannelHandler.Sharable
public class ThingsInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ThingsInboundHandler.class);
    private final ThingClient thingClient;

    public ThingsInboundHandler(ThingClient thingClient) {
        this.thingClient = thingClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 发送包数据
        thingClient.sendData(DataPacket.from((String) msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
