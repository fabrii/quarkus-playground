/*
 *  Developed by Sofis Solutions
 */
package io.quarkus.playground;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 *
 * @author Sofis Solutions
 */
@Entity
@RevisionEntity(RevListener.class)
@Table(name = "revinfo")
public class RevEntity  {   
    
    @Id
    @GeneratedValue
    @RevisionNumber
    private Long rev;

    @RevisionTimestamp
    private Date revtstmp;
    
    private Long revuser;

   
    public Long getRev() {
        return rev;
    }

    public void setRev(Long rev) {
        this.rev = rev;
    }

    public Date getRevtstmp() {
        return revtstmp;
    }

    public void setRevtstmp(Date revtstmp) {
        this.revtstmp = revtstmp;
    }

    public Long getRevuser() {
        return revuser;
    }

    public void setRevuser(Long revuser) {
        this.revuser = revuser;
    }

    
  
    


}

