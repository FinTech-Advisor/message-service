package org.advisor.message.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -617290247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath gid = createString("gid");

    public final StringPath mid = createString("mid");

    public final StringPath name = createString("name");

    public final BooleanPath notice = createBoolean("notice");

    public final org.advisor.member.entities.QMember receiver;

    public final org.advisor.member.entities.QMember sender;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.advisor.message.constants.MessageStatus> status = createEnum("status", org.advisor.message.constants.MessageStatus.class);

    public final StringPath subject = createString("subject");

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new org.advisor.member.entities.QMember(forProperty("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new org.advisor.member.entities.QMember(forProperty("sender")) : null;
    }

}

