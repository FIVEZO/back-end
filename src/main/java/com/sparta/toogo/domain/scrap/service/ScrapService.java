package com.sparta.toogo.domain.scrap.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.scrap.dto.ScrapResponseDto;
import com.sparta.toogo.domain.scrap.entity.QScrap;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.scrap.repository.ScrapRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.sparta.toogo.domain.scrap.entity.QScrap.scrap;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScrapService {

    private final PostRepository postRepository;
    private final ScrapRepository scrapRepository;
    private final JPAQueryFactory queryFactory;


    public ScrapResponseDto scrapPost(User user, Long postId, Long category) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(ErrorCode.NOT_FOUND_DATA.getHttpStatus(), ErrorCode.NOT_FOUND_DATA.getDetail()));

        log.info("post 동작중!");
        Category.PostCategory categoryEnum = Category.findByNumber(category);

//        if(categoryEnum == null) {
//            throw new ResponseStatusException(ErrorCode.NOT_FOUND_DATA.getHttpStatus(), ErrorCode.NOT_FOUND_DATA.getDetail());
//        }
//        QScrap qScrap = QScrap.scrap;
//        Scrap scrap = queryFactory
//                .selectFrom(qScrap)
//                .where(qScrap.user.eq(user)
//                        .and(qScrap.post.eq(post))
//                        .and(qScrap.category.eq(categoryEnum)))
//                .fetchFirst();
       Scrap scrap = scrapRepository.findByUserAndPostAndCategory(user, post, categoryEnum);

        if(scrap == null) {
            scrap = new Scrap(post, user, categoryEnum);
            scrapRepository.save(scrap);
            post.plusScrapPostSum();
            return new ScrapResponseDto(true, post.getScrapPostSum(), "스크랩 되었습니다.");
        } else {
            scrapRepository.delete(scrap);
            post.minusScrapPostSum();
            return new ScrapResponseDto(false, post.getScrapPostSum(), "스크랩에 실패했습니다.");
        }
    }


//    public List<Scrap> getScrapsByCategory(Category.PostCategory category) {
//        return queryFactory
//                .selectFrom(QScrap.scrap)
//                .where(QScrap.scrap.category.eq(category))
//                .fetch();
//    }

//    private Post getPostById(Long postId) {
//        return postRepository.findById(postId)
//                .orElseThrow(() -> new ResponseStatusException(ErrorCode.NOT_FOUND_DATA.getHttpStatus(), ErrorCode.NOT_FOUND_DATA.getDetail()));
//    }
}
