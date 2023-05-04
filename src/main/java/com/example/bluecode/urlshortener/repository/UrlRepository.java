package com.example.bluecode.urlshortener.repository;

import com.example.bluecode.urlshortener.model.UrlModel;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UrlRepository extends R2dbcRepository<UrlModel, String> {

    @Query("SELECT * FROM UrlModel order by access limit 100")
    Flux<UrlModel> findTop100RankedByAccess();

    Mono<UrlModel> findByShorten(String shorten);

    @Query("Update UrlModel set access = :access where shorten = :shorten")
    Mono<Void> updateByShorten(String shorten, Integer access);

}
