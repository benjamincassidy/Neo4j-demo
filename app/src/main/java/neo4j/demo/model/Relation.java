package neo4j.demo.model;

import neo4j.demo.domain.MemberOf;

public class Relation {
    public String type;
    public String direction;
    public String begin;
    public String end;
    public Artist artist;

    public MemberOf toDomain(Artist group) {
        return new MemberOf(group.toDomain(), artist.toDomain(), begin, end);
    }
}
