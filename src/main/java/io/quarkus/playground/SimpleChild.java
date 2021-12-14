package io.quarkus.playground;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class)
@Audited
public class SimpleChild {

    @Id
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SimpleChild{" + "id=" + id + '}';
    }


    
    
    
    
}
