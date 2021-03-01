package neo4j.demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Artist")
public class Artist {

    @Id
    private String id;
    @Property
    private String name;
    @Property
    private String type;
    @Property
    private String disambiguation;

    @Relationship(type = Constants.PERFORMS_ON, direction = Relationship.OUTGOING)
    private Set<Release> recordings = new HashSet<>();

    public Artist() {
    }

    public Artist(String id, String name, String type, String disambiguation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.disambiguation = disambiguation;
    }

    public void addRecording(Release recording) {
        recordings.add(recording);
    }

    public String getId() {
        return id;
    }
}
