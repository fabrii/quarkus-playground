package io.quarkus.playground;

import java.util.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.logmanager.Level;

@Path("/entity")
public class EntityResource {

    private static final Logger LOGGER = Logger.getLogger(EntityResource.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @POST
    @Path("/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public ParentWithTwoLists create(@PathParam long id) {

        SimpleChild s = new SimpleChild();
        s.setId(id);

        entityManager.persist(s);

        ParentWithTwoLists p = new ParentWithTwoLists();
        p.setId(id);
        p.setChild(s);

        entityManager.persist(p);

        ParentWithTwoLists pp = entityManager.find(ParentWithTwoLists.class, id);
        LOGGER.log(Level.INFO, "PARENT CREATED WITH CHILD: " + pp.getChild());

        return p;

    }

    @PUT
    @Path("/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Boolean update(@PathParam long id, ParentWithTwoLists parent) {
        parent.setChild(null);
        parent.setName("asd");
        parent = entityManager.merge(parent);
        LOGGER.log(Level.INFO, "CHILD: " + parent.getChild());
        // Child is null, as expected. But its not propagated to database.
        /*
        Hibernate: 
            update
                ParentWithTwoLists 
            set
                name=? 
            where
                id=?
         */
        
        //Hibernate not doing set child = null.
        return parent.getChild() == null;

    }

    @GET
    @Path("/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Boolean get(@PathParam long id) {
        ParentWithTwoLists parent = entityManager.find(ParentWithTwoLists.class, id);
        LOGGER.log(Level.INFO, "CHILD: " + parent.getChild()); //  Child not null ¿?
        return parent.getChild() == null;

    }
}
