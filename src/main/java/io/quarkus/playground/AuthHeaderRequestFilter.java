package io.quarkus.playground;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class AuthHeaderRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().add("Authorization", "Bearer ASD");
    }

}
