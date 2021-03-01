package neo4j.demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Release")
public class Release {

    @Id
    private String id;
    @Property
    private String title;
    @Property
    private String date;
    @Property
    private String type;

    @Relationship(type = Constants.PERFORMS_ON, direction = Relationship.INCOMING)
    private Set<Artist> artists = new HashSet<>();

    public Release(String id, String title, String date, String type) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.type = type;
    }
}
