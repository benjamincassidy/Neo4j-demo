package neo4j.demo.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = Constants.MEMBER_OF)
public class MemberOf {

    @Id
    String id;

    @EndNode
    Artist group;

    @StartNode
    Artist member;

    @Property
    String start;

    @Property
    String end;

    public MemberOf() {
    }

    public MemberOf(Artist group, Artist member, String start, String end) {
        this.id = group.getId() + "-" + member.getId();
        this.group = group;
        this.member = member;
        this.start = start;
        this.end = end;
    }
}
