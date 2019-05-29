package com.hbd.socket.server.controller;

import com.hbd.socket.server.domain.ReplyMessage;
import com.hbd.socket.server.netty.ChannelRegistry;
import com.hbd.socket.server.netty.IndexRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Optional;

/**
 * @author Ren Xunxiao
 * @date 2018/10/22
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/22
 */
@RequestMapping("/channel")
@RestController
public class ChannelController {
    private static Logger logger = LoggerFactory.getLogger(ChannelController.class);
    private ChannelRegistry channelRegistry;
    private IndexRegistry indexRegistry;

    public ChannelController(ChannelRegistry channelRegistry, IndexRegistry indexRegistry) {
        this.channelRegistry = channelRegistry;
        this.indexRegistry = indexRegistry;
    }

    @PostMapping("/{index}")
    //index站点编号
    public DeferredResult<ResponseEntity<Void>> reply(@PathVariable("index") String index,
                                                      @RequestBody ReplyMessage replyMessage) {
        DeferredResult<ResponseEntity<Void>> result = new DeferredResult<>();
        Optional<Channel> channelOptional = indexRegistry.find(index);
        if (channelOptional.isPresent()) {

            Channel channel = channelOptional.get();
            ByteBuf buffer = channel.alloc().buffer();
            replyMessage.write(buffer);
            ChannelFuture future = channel.writeAndFlush(buffer);
            future.addListener(f -> {
                if (f.isSuccess()) {
                    result.setResult(ResponseEntity.ok().build());
                } else {
                    logger.error("write to channel {}", channelOptional);
                    result.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                }
            });
        } else {
            logger.error("index {} not found", index);
            result.setErrorResult(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return result;
    }

}
