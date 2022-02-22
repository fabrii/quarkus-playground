package io.quarkus.playground;

import java.io.Closeable;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/entity")
@Consumes(MediaType.APPLICATION_JSON)
public interface ClientInterface extends Closeable {
    
    @GET
    @Path("/{id}/is-contained-initialized/")
    public boolean isContainedInitialized(@PathParam long id);
 
}



    