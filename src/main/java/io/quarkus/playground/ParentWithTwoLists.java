package io.quarkus.playground;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.envers.Audited;

@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class)
@Audited
public class ParentWithTwoLists {

    @Id
    private Long id;

    private String name;
    
    @ManyToOne(optional = true)
    private SimpleChild child;
    
    @OneToMany(mappedBy = "parent")
    private List<Contained1> contained;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleChild getChild() {
        return child;
    }

    public void setChild(SimpleChild child) {
        this.child = child;
    }

    public List<Contained1> getContained() {
        return contained;
    }

    public void setContained(List<Contained1> contained) {
        this.contained = contained;
    }

    

    

}
