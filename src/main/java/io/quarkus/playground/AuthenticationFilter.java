package io.quarkus.playground;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;

/**
 * Filtro de request para validar autenticación y autorización
 */
@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer ";

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());


    @Inject
    OperationsBean operationsBean;

    /**
     * Filtro encargado de verificar si el endpoint tiene seguridad y delegar
     * autorización al método performAuthorization
     *
     * @param requestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            LOGGER.log(Level.INFO, "Executing filter AuthenticationFilter");

            String token = getBearerToken(requestContext);

            Set<String> operations = operationsBean.getOperations();
            operations.add("AUTH");

           
            requestContext.setSecurityContext(getSecurityContext());

         

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            requestContext.abortWith(Response.status(401).build());
        }

    }

    /**
     * Crea security context para consumos con token y sin token
     *
     * @param user puede ser null
     * @return
     */
    private static SecurityContext getSecurityContext() {
        return new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return "test";
                    }
                };
            }

            @Override
            public boolean isUserInRole(String r) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        };
    }

    /**
     * Extrae el bearer token de un request
     *
     * @param request
     * @return bearer token
     */
    private String getBearerToken(ContainerRequestContext request) {
        String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_VALUE_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_VALUE_PREFIX.length());
        }
        return null;
    }

}
