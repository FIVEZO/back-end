package com.sparta.toogo.domain.scrap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScrap is a Querydsl query type for Scrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScrap extends EntityPathBase<Scrap> {

    private static final long serialVersionUID = -2071365259L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScrap scrap = new QScrap("scrap");

    public final com.sparta.toogo.global.utill.QTimestamped _super = new com.sparta.toogo.global.utill.QTimestamped(this);

    public final EnumPath<com.sparta.toogo.domain.post.entity.Category.PostCategory> category = createEnum("category", com.sparta.toogo.domain.post.entity.Category.PostCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.sparta.toogo.domain.post.entity.QPost post;

    public final com.sparta.toogo.domain.user.entity.QUser user;

    public QScrap(String variable) {
        this(Scrap.class, forVariable(variable), INITS);
    }

    public QScrap(Path<? extends Scrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScrap(PathMetadata metadata, PathInits inits) {
        this(Scrap.class, metadata, inits);
    }

    public QScrap(Class<? extends Scrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.sparta.toogo.domain.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.toogo.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

