package com.sparta.toogo.domain.messageroom.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageRoom is a Querydsl query type for MessageRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageRoom extends EntityPathBase<MessageRoom> {

    private static final long serialVersionUID = 219765109L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageRoom messageRoom = new QMessageRoom("messageRoom");

    public final com.sparta.toogo.global.util.QTimestamped _super = new com.sparta.toogo.global.util.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.sparta.toogo.domain.message.entity.Message, com.sparta.toogo.domain.message.entity.QMessage> messageList = this.<com.sparta.toogo.domain.message.entity.Message, com.sparta.toogo.domain.message.entity.QMessage>createList("messageList", com.sparta.toogo.domain.message.entity.Message.class, com.sparta.toogo.domain.message.entity.QMessage.class, PathInits.DIRECT2);

    public final com.sparta.toogo.domain.post.entity.QPost post;

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final StringPath roomId = createString("roomId");

    public final StringPath roomName = createString("roomName");

    public final StringPath sender = createString("sender");

    public final com.sparta.toogo.domain.user.entity.QUser user;

    public QMessageRoom(String variable) {
        this(MessageRoom.class, forVariable(variable), INITS);
    }

    public QMessageRoom(Path<? extends MessageRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageRoom(PathMetadata metadata, PathInits inits) {
        this(MessageRoom.class, metadata, inits);
    }

    public QMessageRoom(Class<? extends MessageRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.sparta.toogo.domain.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.toogo.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

