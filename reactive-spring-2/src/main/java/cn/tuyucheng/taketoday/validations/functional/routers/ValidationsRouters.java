package cn.tuyucheng.taketoday.validations.functional.routers;

import cn.tuyucheng.taketoday.validations.functional.handlers.FunctionalHandler;
import cn.tuyucheng.taketoday.validations.functional.handlers.impl.AnnotatedRequestEntityValidationHandler;
import cn.tuyucheng.taketoday.validations.functional.handlers.impl.CustomRequestEntityValidationHandler;
import cn.tuyucheng.taketoday.validations.functional.handlers.impl.OtherEntityValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ValidationsRouters {

    @Bean
    public RouterFunction<ServerResponse> validationsRouter(@Autowired CustomRequestEntityValidationHandler dryHandler,
                                                            @Autowired FunctionalHandler complexHandler,
                                                            @Autowired OtherEntityValidationHandler otherHandler,
                                                            @Autowired AnnotatedRequestEntityValidationHandler annotatedEntityHandler) {
        return route(POST("/complex-handler-functional-validation"), complexHandler::handleRequest)
                .andRoute(POST("/dry-functional-validation"), dryHandler::handleRequest)
                .andRoute(POST("/other-dry-functional-validation"), otherHandler::handleRequest)
                .andRoute(POST("/annotated-functional-validation"), annotatedEntityHandler::handleRequest);
    }

    @Bean
    public RouterFunction<ServerResponse> functionalRoute(FunctionalHandler handler) {
        return route(POST("/functional-endpoint"), handler::handleRequest);
    }
}