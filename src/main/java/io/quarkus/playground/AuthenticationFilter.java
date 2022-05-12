package io.quarkus.playground;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
    @Context
    private ResourceInfo resourceInfo;
    
    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";
    
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        try {
            
            LOGGER.log(Level.INFO, "Filter");
            
            Method method = resourceInfo.getResourceMethod();

            // @DenyAll on the method takes precedence over @RolesAllowed and @PermitAll
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(403).build());
            }

            // @RolesAllowed on the method takes precedence over @PermitAll
            RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
            if (rolesAllowed != null) {
                performAuthorization(rolesAllowed.value(), requestContext);
                return;
            }

            // @PermitAll on the method takes precedence over @RolesAllowed on the class
            if (method.isAnnotationPresent(PermitAll.class)) {
                // Do nothing
                return;
            }

            // @DenyAll can't be attached to classes
            // @RolesAllowed on the class takes precedence over @PermitAll on the class
            rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
            if (rolesAllowed != null) {
                performAuthorization(rolesAllowed.value(), requestContext);
                LOGGER.log(Level.INFO, "Return");
                return;
            }

            // @PermitAll on the class
            if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
                // Do nothing
                return;
            }

            LOGGER.log(Level.INFO, "Aborted");
            // Todos los métodos deben estar anotados
            requestContext.abortWith(Response.status(403).build());
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            requestContext.abortWith(Response.status(401).build());
        }
        
    }
    
    private String getBearerToken(ContainerRequestContext request) {
        String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_VALUE_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_VALUE_PREFIX.length());
        }
        return null;
    }
    
    private void performAuthorization(String[] operationsAllowed, ContainerRequestContext requestContext) {
        try {
            String token = getBearerToken(requestContext);

            //Validate token
            boolean validated = true;
            
            if (validated) {
                LOGGER.log(Level.INFO, "Validated");
                return;
            }
            
            LOGGER.log(Level.INFO, "Aborted");
            requestContext.abortWith(Response.status(403).build());
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            requestContext.abortWith(Response.status(401).build());
        }
    }
    
}
