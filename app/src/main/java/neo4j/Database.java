package neo4j;

import java.util.List;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import neo4j.demo.domain.Artist;

public class Database {

    public void addArtistAndReleases(neo4j.demo.model.Artist artist, List<neo4j.demo.model.Release> releases) {
        Configuration configuration = new Configuration.Builder().uri("bolt://localhost")
                .credentials("neo4j", "password").build();
        SessionFactory sf = new SessionFactory(configuration, "neo4j.demo.domain");
        Session session = sf.openSession();
        Artist artist2 = artist.toDomain();
        releases.stream().forEach((r) -> {
            artist2.addRecording(r.toDomain());
        });
        session.save(artist2);
    }

}
