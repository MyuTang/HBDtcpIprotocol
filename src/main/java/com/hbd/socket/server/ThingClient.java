package com.hbd.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Ren Xunxiao
 * @date 2018/10/11
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/11
 */
@Component
public class ThingClient {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private RestTemplate restTemplate;

    private List<String> urls;


    public ThingClient() {
        restTemplate = new RestTemplateBuilder().build();
    }

    @Value("#{'${thingsServiceUrl}'.split(',')}")
    public void setUrl(List<String> urls) {
        logger.info("url: {}", urls);
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    @Async
    public void sendData(DataPacket dataPacket) {
        Objects.requireNonNull(getUrls());
        getUrls().forEach(url -> CompletableFuture.runAsync(() -> {
            try {
                logger.info("发送数据 url: {}", url);
                URI uri = restTemplate.postForLocation(url, dataPacket);
                logger.info("发送成功 url: {}", uri);
            } catch (Exception e) {
                logger.error("POST {} {}", url, e.getMessage(), e.getCause());
            }
        }));
    }
}
