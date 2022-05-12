package com.nttdata.repository;


import com.nttdata.document.BuySale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface BuySaleRepository extends ReactiveMongoRepository<BuySale,String> {

    Mono<BuySale> findByDate(LocalDate date);

}
