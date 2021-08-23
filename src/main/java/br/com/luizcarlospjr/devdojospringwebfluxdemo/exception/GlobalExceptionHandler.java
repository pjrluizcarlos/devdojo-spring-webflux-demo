package br.com.luizcarlospjr.devdojospringwebfluxdemo.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final CustomAttributes customAttributes;

    public GlobalExceptionHandler(  ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                    ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer,
                                    CustomAttributes customAttributes) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.customAttributes = customAttributes;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        ErrorAttributeOptions errorAttributeOptions = getErrorAttributeOptions(request);
        Map<String, Object> errorAttributes = customAttributes.getErrorAttributes(request, errorAttributeOptions);
        Integer status = (Integer) errorAttributes.get("status");

        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributes));
    }

    private ErrorAttributeOptions getErrorAttributeOptions(ServerRequest request) {
        boolean hasTraceParameter = hasTraceParameter(request);

        return hasTraceParameter
                ? ErrorAttributeOptions.of(Include.STACK_TRACE)
                : ErrorAttributeOptions.defaults();
    }

    private Boolean hasTraceParameter(ServerRequest request) {
        return request.queryParam("trace")
                .map("true"::equals)
                .orElse(false);
    }

}
