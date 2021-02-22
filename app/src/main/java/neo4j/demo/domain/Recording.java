package neo4j.demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Recording {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int released;

    @Relationship(type = Constants.PERFORMS_ON, direction = Relationship.INCOMING)
    private Set<Artist> artists = new HashSet<>();

    public Recording() {
    }

    public Recording(String title, int released) {
        this.title = title;
        this.released = released;
    }
}
