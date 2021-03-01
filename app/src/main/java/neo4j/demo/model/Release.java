package neo4j.demo.model;

import com.google.gson.annotations.SerializedName;

public class Release {
    public String id;
    @SerializedName("first-release-date")
    public String date;
    public String title;
    @SerializedName("primary-type")
    public String type;

    public neo4j.demo.domain.Release toDomain() {
        return new neo4j.demo.domain.Release(id, title, date, type);
    }
}
