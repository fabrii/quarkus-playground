package io.quarkus.playground;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;

@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class)
@Audited
public class Contained1 {

    @Id
    private Long id;

    @ManyToOne
    private ParentWithTwoLists parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentWithTwoLists getParent() {
        return parent;
    }

    public void setParent(ParentWithTwoLists parent) {
        this.parent = parent;
    }

}
