package org.advisor.message.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -617290247L;

    public static final QMessage message = new QMessage("message");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath gid = createString("gid");

    public final SimplePath<org.advisor.member.MemberUtil> memberUtil = createSimple("memberUtil", org.advisor.member.MemberUtil.class);

    public final StringPath mid = createString("mid");

    public final StringPath name = createString("name");

    public final BooleanPath notice = createBoolean("notice");

    public final StringPath receiver = createString("receiver");

    public final StringPath sender = createString("sender");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.advisor.message.constants.MessageStatus> status = createEnum("status", org.advisor.message.constants.MessageStatus.class);

    public final StringPath subject = createString("subject");

    public QMessage(String variable) {
        super(Message.class, forVariable(variable));
    }

    public QMessage(Path<? extends Message> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessage(PathMetadata metadata) {
        super(Message.class, metadata);
    }

}

