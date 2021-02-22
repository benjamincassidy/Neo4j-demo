package neo4j.demo;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import neo4j.demo.domain.Artist;
import neo4j.demo.domain.Recording;

public class App {

    public void connectToNeo4j() {
        ConfigurationSource source = new ClasspathConfigurationSource("ogm.properties");
        Configuration config = new Configuration.Builder(source).build();
        SessionFactory sessionFactory = new SessionFactory(config, "neo4j.demo.domain");
        Session session = sessionFactory.openSession();

        Artist kurt = new Artist("Kurt Cobain");
        Recording nevermind = new Recording("Nevermind", 1991);
        kurt.addRecording(nevermind);
        session.save(kurt);
        sessionFactory.close();
    }

    public static void main(String[] args) {
        App app = new App();
        app.connectToNeo4j();
    }
}
