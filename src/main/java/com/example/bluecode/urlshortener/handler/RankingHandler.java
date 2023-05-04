package com.example.bluecode.urlshortener.handler;

import com.example.bluecode.urlshortener.model.UrlModel;
import com.example.bluecode.urlshortener.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RankingHandler {

    @Autowired
    ShortenerService shortenerService;

    public Mono<ServerResponse> rankingUrl(ServerRequest serverRequest) {
        return ServerResponse.ok().body(shortenerService.getRanking(), UrlModel.class);
    }

}
