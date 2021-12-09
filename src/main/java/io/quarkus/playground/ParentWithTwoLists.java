package io.quarkus.playground;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ParentWithTwoLists {

    @Id
    private Long id;

    private String name;
    
    @ManyToOne(optional = true)
    private SimpleChild child;
    
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Contained1> contained;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Contained2> contained2;


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

    public List<Contained2> getContained2() {
        return contained2;
    }

    public void setContained2(List<Contained2> contained2) {
        this.contained2 = contained2;
    }


    
    
    
    
    

    

}
