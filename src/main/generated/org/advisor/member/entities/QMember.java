package org.advisor.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -405233557L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final StringPath addressSub = createString("addressSub");

    public final ListPath<Authorities, QAuthorities> authorities = this.<Authorities, QAuthorities>createList("authorities", Authorities.class, QAuthorities.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> birthDt = createDate("birthDt", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> credentialChangedAt = createDateTime("credentialChangedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final StringPath mid = createString("mid");

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath optionalTerms = createString("optionalTerms");

    public final StringPath password = createString("password");

    public final BooleanPath requiredTerms1 = createBoolean("requiredTerms1");

    public final BooleanPath requiredTerms2 = createBoolean("requiredTerms2");

    public final BooleanPath requiredTerms3 = createBoolean("requiredTerms3");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath socialToken = createString("socialToken");

    public final StringPath zipCode = createString("zipCode");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

