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

import com.hbd.socket.server.netty.handler.ByteArrayToHexStringDecoder;
import com.hbd.socket.server.netty.handler.IndexExtractHandler;
import com.hbd.socket.server.netty.handler.ThingFrameDecode;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 通道初始化
 *
 * @author HJX
 * @date 2018/9/28
 * @copyright Hanboard
 * Created by HJX on 2018/9/17.
 */
@Component
@Qualifier("thingsChannelInitializer")
public class ThingsChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ChannelInboundHandlerAdapter thingsInboundHandler;

    private final ChannelInboundHandler channelTrackHandler;
    private final ChannelInboundHandler timeSyncHandler;
    private final IndexExtractHandler indexExtractHandler;

    @Autowired
    public ThingsChannelInitializer(@Qualifier("thingsInboundHandler") ChannelInboundHandlerAdapter thingsInboundHandler,
                                    @Qualifier("channelTrack") ChannelInboundHandler channelTrackHandler,
                                    @Qualifier("timeSyncHandler") ChannelInboundHandler timeSyncHandler,
                                    IndexExtractHandler indexExtractHandler) {
        this.thingsInboundHandler = thingsInboundHandler;
        this.channelTrackHandler = channelTrackHandler;
        this.timeSyncHandler = timeSyncHandler;
        this.indexExtractHandler = indexExtractHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(channelTrackHandler);
        pipeline.addLast(new ThingFrameDecode());
        pipeline.addLast(indexExtractHandler);
        pipeline.addLast(timeSyncHandler);
        pipeline.addLast(new ByteArrayToHexStringDecoder());
        pipeline.addLast(thingsInboundHandler);
    }
}
