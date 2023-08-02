package com.sparta.toogo.domain.kakao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKakao is a Querydsl query type for Kakao
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKakao extends EntityPathBase<Kakao> {

    private static final long serialVersionUID = -1319497035L;

    public static final QKakao kakao = new QKakao("kakao");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QKakao(String variable) {
        super(Kakao.class, forVariable(variable));
    }

    public QKakao(Path<? extends Kakao> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKakao(PathMetadata metadata) {
        super(Kakao.class, metadata);
    }

}

