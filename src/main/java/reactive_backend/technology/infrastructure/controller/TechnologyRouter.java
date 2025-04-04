package reactive_backend.technology.infrastructure.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_backend.technology.application.http.handler.ITechnologyHandler;
import reactive_backend.technology.domain.util.ConstRoute;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TechnologyRouter {

    @Bean
    public RouterFunction<ServerResponse> technologyRoutes(ITechnologyHandler technologyHandler) {
        return route(POST(ConstRoute.TECHNOLOGY_REST_ROUTE + ConstRoute.CREATE_TECHNOLOGY_REST_ROUTE),
                technologyHandler::saveTechnology)
                .andRoute(POST(ConstRoute.TECHNOLOGY_REST_ROUTE + ConstRoute.LIST_TECHNOLOGY_REST_ROUTE),
                        technologyHandler::listTechnology);
    }
}
