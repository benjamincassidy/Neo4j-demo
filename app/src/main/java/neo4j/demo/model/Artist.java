package neo4j.demo.model;

import java.util.List;

public class Artist {

    public String id;
    public String type;
    public String name;
    public String disambiguation;
    public List<Release> releases;

    public neo4j.demo.domain.Artist toDomain() {
        return new neo4j.demo.domain.Artist(id, name, type, disambiguation);
    }
}
