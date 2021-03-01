package neo4j.demo.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Artist {

    public String id;
    public String type;
    public String name;
    public String disambiguation;
    @SerializedName("release-groups")
    public List<Release> releases;

    public neo4j.demo.domain.Artist toDomain() {
        neo4j.demo.domain.Artist artist = new neo4j.demo.domain.Artist(id, name, type, disambiguation);
        releases.forEach((r) -> {
            artist.addRecording(r.toDomain());
        });
        return artist;
    }
}
