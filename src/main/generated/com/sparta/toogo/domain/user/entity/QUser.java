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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.sparta.toogo.global.util.QTimestamped _super = new com.sparta.toogo.global.util.QTimestamped(this);

    public final ListPath<com.sparta.toogo.domain.comment.entity.Comment, com.sparta.toogo.domain.comment.entity.QComment> comments = this.<com.sparta.toogo.domain.comment.entity.Comment, com.sparta.toogo.domain.comment.entity.QComment>createList("comments", com.sparta.toogo.domain.comment.entity.Comment.class, com.sparta.toogo.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath emoticon = createString("emoticon");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> kakaoId = createNumber("kakaoId", Long.class);

    public final com.sparta.toogo.domain.mypage.entity.QMyPage myPage;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.sparta.toogo.domain.post.entity.Post, com.sparta.toogo.domain.post.entity.QPost> posts = this.<com.sparta.toogo.domain.post.entity.Post, com.sparta.toogo.domain.post.entity.QPost>createList("posts", com.sparta.toogo.domain.post.entity.Post.class, com.sparta.toogo.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<UserRoleEnum> role = createEnum("role", UserRoleEnum.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myPage = inits.isInitialized("myPage") ? new com.sparta.toogo.domain.mypage.entity.QMyPage(forProperty("myPage"), inits.get("myPage")) : null;
    }

}

