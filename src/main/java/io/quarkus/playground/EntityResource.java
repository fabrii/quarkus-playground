package io.quarkus.playground;

import io.opentelemetry.api.baggage.Baggage;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.hibernate.Hibernate;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/entity")
public class EntityResource {

    private static final Logger LOGGER = Logger.getLogger(EntityResource.class.getName());

    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    RestClient client;

    @PUT
    @Path("/{id}/")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public void create(@PathParam long id) {
        Containing containing = new Containing();
        containing.setId(id);

        Containing2 containing2 = new Containing2();
        containing2.setId(id);
        entityManager.persist(containing2);

        for (int i = 0; i < 5; i++) {
            Contained contained = new Contained();
            contained.setContaining(containing);
            contained.setContaining2(containing2);
            containing.getContained().add(contained);
        }

        entityManager.persist(containing);
        
        //Propagation working
        client.isContainedInitialized(id);
    }

    @GET
    @Path("/{id}/is-contained-initialized/")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isContainedInitialized(@PathParam long id) {
        return true;
    }

   
}
