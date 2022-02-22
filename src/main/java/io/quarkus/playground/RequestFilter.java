package io.quarkus.playground;

import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.api.trace.Span;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(RequestFilter.class.getName());

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    RestClient client;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        //Obtener los datos propios de la invocación
        String restMethod = requestContext.getMethod(); //GET, POST; etc
        String baseUri = requestContext.getUriInfo().getBaseUri().toString();
        baseUri = baseUri.substring(0, baseUri.length() - 1);
        String path = requestContext.getUriInfo().getPath();
        String queryParams = requestContext.getUriInfo().getRequestUri().getQuery();
        
        if (path.contains("is-initia"))

        Span span = Span.current();

        LOGGER.log(Level.SEVERE, "SPAN CONTEXT " + span.getSpanContext().getSpanId());
        LOGGER.log(Level.SEVERE, "TRACE ID " + span.getSpanContext().getTraceId());
        LOGGER.log(Level.SEVERE, Baggage.current().getEntryValue("system"));
        LOGGER.log(Level.SEVERE, requestContext.getHeaderString("system"));
        LOGGER.log(Level.SEVERE, requestContext.getHeaders().toString());

        Baggage.current()
                .toBuilder()
                .put("system", "systemX")
                .build()
                .makeCurrent();

    }
}
