package com.sparta.toogo.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -321987409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.sparta.toogo.global.utill.QTimestamped _super = new com.sparta.toogo.global.utill.QTimestamped(this);

    public final EnumPath<Category.PostCategory> category = createEnum("category", Category.PostCategory.class);

    public final ListPath<com.sparta.toogo.domain.comment.entity.Comment, com.sparta.toogo.domain.comment.entity.QComment> commentList = this.<com.sparta.toogo.domain.comment.entity.Comment, com.sparta.toogo.domain.comment.entity.QComment>createList("commentList", com.sparta.toogo.domain.comment.entity.Comment.class, com.sparta.toogo.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    public final StringPath country = createString("country");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath meetDate = createString("meetDate");

    public final ListPath<com.sparta.toogo.domain.messageroom.entity.MessageRoom, com.sparta.toogo.domain.messageroom.entity.QMessageRoom> messageRoom = this.<com.sparta.toogo.domain.messageroom.entity.MessageRoom, com.sparta.toogo.domain.messageroom.entity.QMessageRoom>createList("messageRoom", com.sparta.toogo.domain.messageroom.entity.MessageRoom.class, com.sparta.toogo.domain.messageroom.entity.QMessageRoom.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.sparta.toogo.domain.notification.entity.Notification, com.sparta.toogo.domain.notification.entity.QNotification> notificationList = this.<com.sparta.toogo.domain.notification.entity.Notification, com.sparta.toogo.domain.notification.entity.QNotification>createList("notificationList", com.sparta.toogo.domain.notification.entity.Notification.class, com.sparta.toogo.domain.notification.entity.QNotification.class, PathInits.DIRECT2);

    public final StringPath people = createString("people");

    public final ListPath<com.sparta.toogo.domain.scrap.entity.Scrap, com.sparta.toogo.domain.scrap.entity.QScrap> scrapList = this.<com.sparta.toogo.domain.scrap.entity.Scrap, com.sparta.toogo.domain.scrap.entity.QScrap>createList("scrapList", com.sparta.toogo.domain.scrap.entity.Scrap.class, com.sparta.toogo.domain.scrap.entity.QScrap.class, PathInits.DIRECT2);

    public final NumberPath<Long> scrapPostSum = createNumber("scrapPostSum", Long.class);

    public final StringPath title = createString("title");

    public final com.sparta.toogo.domain.user.entity.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.toogo.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

