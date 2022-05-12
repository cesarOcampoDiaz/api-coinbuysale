package com.nttdata.router;



import com.nttdata.handler.BuySaleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BuySaleRouter {
    @Bean
    public RouterFunction<ServerResponse> buysaleRouterFunc(BuySaleHandler buySaleHandler) {
        return RouterFunctions.route(POST("/buysale").and(accept(MediaType.APPLICATION_JSON)), buySaleHandler::add)
                .andRoute(GET("/buysale").and(accept(MediaType.TEXT_EVENT_STREAM)), buySaleHandler::findAll)
                .andRoute(GET("/buysale/{id}").and(accept(MediaType.APPLICATION_JSON)), buySaleHandler::findById)
                .andRoute(PUT("/buysale/{id}").and(accept(MediaType.APPLICATION_JSON)), buySaleHandler::update)
                .andRoute(GET("/buysale/date/{date}").and(accept(MediaType.TEXT_EVENT_STREAM)), buySaleHandler::findByDate)
                ;


    }

}
