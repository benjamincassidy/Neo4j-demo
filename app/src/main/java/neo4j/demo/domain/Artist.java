package neo4j.demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Artist {

    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String name;

    @Relationship(type = Constants.PERFORMS_ON, direction = Relationship.OUTGOING)
    private Set<Recording> recordings = new HashSet<>();

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public void addRecording(Recording recording) {
        recordings.add(recording);
    }
}
