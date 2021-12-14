package io.quarkus.playground;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;

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
        
        Contained1 c1 = new Contained1();
        c1.setId(1L);
        c1.setParent(p);
        
        entityManager.persist(c1);


        return p;

    }

    @GET
    @Path("/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Boolean getRevision(@PathParam long id) {

        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<ParentWithTwoLists> respuesta = reader.createQuery().forEntitiesAtRevision(ParentWithTwoLists.class, 1L)
                .add(AuditEntity.id().eq(id))
                .getResultList();
        if (respuesta != null && respuesta.size() > 0) {
            ParentWithTwoLists ret = respuesta.get(0);
            
            LOGGER.log(Level.INFO, "IS INITIALIZED CHILD: " + Hibernate.isInitialized(ret.getChild())); //I thought *ToOne associations were also Lazy with envers. That changed?
            LOGGER.log(Level.INFO, "IS INITIALIZED CONTAINED: " + Hibernate.isInitialized(ret.getContained())); //Collections should be lazy.
           
            return Hibernate.isInitialized(ret.getContained());
        }
        return null;

    }
    
}
