package neo4j.demo.model;

public class Release {
    public String id;
    public String date;
    public String title;
    public String status;

    public neo4j.demo.domain.Release toDomain() {
        return new neo4j.demo.domain.Release(id, title, date, status);
    }
}
