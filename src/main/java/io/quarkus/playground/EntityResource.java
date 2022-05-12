package io.quarkus.playground;

import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/entity")
public class EntityResource {

    private static final Logger LOGGER = Logger.getLogger(EntityResource.class.getName());


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    @RolesAllowed({"ROL1"})
    public boolean test(@PathParam long id) {
        return true;
    }

   
}
