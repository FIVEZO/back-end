package com.sparta.toogo.domain.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyPage is a Querydsl query type for MyPage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyPage extends EntityPathBase<MyPage> {

    private static final long serialVersionUID = 741704837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyPage myPage = new QMyPage("myPage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final com.sparta.toogo.domain.user.entity.QUser user;

    public QMyPage(String variable) {
        this(MyPage.class, forVariable(variable), INITS);
    }

    public QMyPage(Path<? extends MyPage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyPage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyPage(PathMetadata metadata, PathInits inits) {
        this(MyPage.class, metadata, inits);
    }

    public QMyPage(Class<? extends MyPage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.toogo.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

