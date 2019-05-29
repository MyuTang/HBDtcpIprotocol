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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


/**TCP配置，端口号，最大连接数，是否保持长连接
 * @author HJX
 * @date 2018/9/28
 * @copyright Hanboard
 * Created by HJX on 2018/9/17.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Component
public class TCPServer {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress tcpPort;


    private Channel serverChannel;

    public void start() throws Exception {
        serverChannel =  serverBootstrap.bind(tcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() {
        if ( serverChannel != null ) {
            serverChannel.close();
            serverChannel.parent().close();
        }
    }
}
