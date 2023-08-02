package com.sparta.toogo.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2064573819L;

    public static final QUser user = new QUser("user");

    public final com.sparta.toogo.global.util.QTimestamped _super = new com.sparta.toogo.global.util.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.sparta.toogo.domain.post.entity.Post, com.sparta.toogo.domain.post.entity.QPost> posts = this.<com.sparta.toogo.domain.post.entity.Post, com.sparta.toogo.domain.post.entity.QPost>createList("posts", com.sparta.toogo.domain.post.entity.Post.class, com.sparta.toogo.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<UserRoleEnum> role = createEnum("role", UserRoleEnum.class);

    public final BooleanPath userStatus = createBoolean("userStatus");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

