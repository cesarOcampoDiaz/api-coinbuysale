package com.nttdata.handler;


import com.nttdata.document.BuySale;
import com.nttdata.repository.BuySaleRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BuySaleHandler {

    private final BuySaleRepository buySaleRepository;


    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();



    @Autowired
    public BuySaleHandler(BuySaleRepository buySaleRepository) {
        this.buySaleRepository = buySaleRepository;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        var buySaleMono = serverRequest.bodyToMono(BuySale.class);

        return buySaleMono.flatMap(t -> {
                    //t.setDate(LocalDate.now());
                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(buySaleRepository.save(t), BuySale.class);
                });
    }


    //public Mono<>

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        var buySaleItem = buySaleRepository.findById(id);


        return buySaleItem.flatMap(t -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buySaleItem, BuySale.class)
                .switchIfEmpty(notFound)
        );
    }

    public Mono<ServerResponse> findByDate(ServerRequest serverRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var date = LocalDate.parse(serverRequest.pathVariable("date"),formatter);

        var buySaleItem = buySaleRepository.findByDate(date);

        return buySaleItem.flatMap(t -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buySaleItem, BuySale.class)
                .switchIfEmpty(notFound)
        );
    }
    

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(buySaleRepository.findAll().log("Func"), BuySale.class).switchIfEmpty(notFound);


    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        var buySaleItem = buySaleRepository.findById(id);
        var buySale = serverRequest.bodyToMono(BuySale.class);

        return buySaleItem.flatMap(
                t -> {
                    return buySale.flatMap(x -> {
                        t.setSaleAmount(x.getSaleAmount());
                        t.setPurchaseAmount(x.getPurchaseAmount());
                        return ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(buySaleRepository.save(t), BuySale.class);
                    });
                });

    }


}
