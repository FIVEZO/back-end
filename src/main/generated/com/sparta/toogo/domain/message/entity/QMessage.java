package com.sparta.toogo.domain.message.entity;

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

    private static final long serialVersionUID = -562122443L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message1 = new QMessage("message1");

    public final com.sparta.toogo.global.util.QTimestamped _super = new com.sparta.toogo.global.util.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final com.sparta.toogo.domain.messageroom.entity.QMessageRoom messageRoom;

    public final StringPath receiver = createString("receiver");

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final StringPath roomId = createString("roomId");

    public final StringPath sender = createString("sender");

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final StringPath sentTime = createString("sentTime");

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
        this.messageRoom = inits.isInitialized("messageRoom") ? new com.sparta.toogo.domain.messageroom.entity.QMessageRoom(forProperty("messageRoom"), inits.get("messageRoom")) : null;
    }

}

