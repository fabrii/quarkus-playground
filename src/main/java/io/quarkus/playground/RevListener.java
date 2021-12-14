/*
 *  Developed by Sofis Solutions
 */
package io.quarkus.playground;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.envers.RevisionListener;

/**
 *
 * @author Sofis Solutions
 */
public class RevListener implements RevisionListener {

    private static final Logger LOGGER = Logger.getLogger(RevListener.class.getName());

    @Override
    public void newRevision(Object revisionEntity) {
        try {

            RevEntity revEntity = (RevEntity) revisionEntity;
            revEntity.setRevuser(1L);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
