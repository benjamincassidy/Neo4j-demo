package neo4j;

import java.util.List;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import neo4j.demo.domain.Artist;

public class Database {

    Configuration configuration = new Configuration.Builder().uri("bolt://localhost").credentials("neo4j", "password")
            .build();
    SessionFactory sf = new SessionFactory(configuration, "neo4j.demo.domain");

    public void addArtistAndReleases(neo4j.demo.model.Artist artist) {
        Session session = sf.openSession();
        Artist artist2 = artist.toDomain();
        session.save(artist2);
    }

    public void addMembersOfBand(neo4j.demo.model.Artist artist) {
        Session session = sf.openSession();
        artist.relations.forEach((r) -> {
            if (r.type.equals("member of band")) {
                session.save(r.toDomain(artist));
            }
        });
    }
}
