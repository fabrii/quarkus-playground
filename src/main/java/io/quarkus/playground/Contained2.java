package io.quarkus.playground;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Contained2 {

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
