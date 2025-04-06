package reactive_backend.technology.infrastructure.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_backend.technology.application.http.handler.IAbilityHandler;
import reactive_backend.technology.application.http.handler.ITechnologyHandler;
import reactive_backend.technology.domain.util.ConstRoute;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TechnologyRouter {

    @Bean
    public RouterFunction<ServerResponse> technologyRoutes(ITechnologyHandler technologyHandler, IAbilityHandler abilityHandler) {
        return route(POST(ConstRoute.TECHNOLOGY_REST_ROUTE + ConstRoute.CREATE_TECHNOLOGY_REST_ROUTE),
                technologyHandler::saveTechnology)
                .andRoute(POST(ConstRoute.TECHNOLOGY_REST_ROUTE + ConstRoute.LIST_TECHNOLOGY_REST_ROUTE),
                        technologyHandler::listTechnology)
                .andRoute(POST(ConstRoute.TECHNOLOGY_REST_ROUTE + ConstRoute.GET_TECHNOLOGIES_BY_NAME_REST_ROUTE),
                        technologyHandler::getTechnologiesByName)
                .andRoute(POST(ConstRoute.TECHNOLOGY_REST_ROUTE+ConstRoute.ADD_ABILITY_REST_ROUTE),
                        abilityHandler::addAbility)
                .andRoute(GET(ConstRoute.TECHNOLOGY_REST_ROUTE+ConstRoute.GET_ALL_BY_ABILITY_ID_REST_ROUTE),
                        abilityHandler::getAllTechnologiesByAbilityId);
    }
}
