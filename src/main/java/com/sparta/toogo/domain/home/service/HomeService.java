package com.sparta.toogo.domain.home.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.toogo.domain.home.dto.HomeCountryCountDto;
import com.sparta.toogo.domain.home.dto.HomeResponseDto;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.entity.QPost;
import com.sparta.toogo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostRepository postRepository;
    private final JPAQueryFactory jpaQueryFactory;


    public List<HomeResponseDto> getHome() {

        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postList = postRepository.findAll(pageable);

        return postList.stream()
                .map(HomeResponseDto::new)
                .toList();
    }


    public HomeCountryCountDto getCountryCount() {

        QPost post = QPost.post;

        Long ASIAPostCount = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.category.eq(Category.PostCategory.ASIA))
                .fetchOne();

        Long AFRICAPostCount = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.category.eq(Category.PostCategory.AFRICA))
                .fetchOne();

        Long EUROPEPostCount = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.category.eq(Category.PostCategory.EUROPE))
                .fetchOne();

        Long OCEANIAPostCount = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.category.eq(Category.PostCategory.OCEANIA))
                .fetchOne();

        Long AMERICAPostCount = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.category.eq(Category.PostCategory.AMERICA))
                .fetchOne();

        return new HomeCountryCountDto(ASIAPostCount, AFRICAPostCount, EUROPEPostCount, OCEANIAPostCount, AMERICAPostCount);
    }
}

//        Long asiaPostCount = getPostCountByCategory(Category.PostCategory.ASIA);
//        Long africaPostCount = getPostCountByCategory(Category.PostCategory.AFRICA);
//        Long europePostCount = getPostCountByCategory(Category.PostCategory.EUROPE);
//        Long oceaniaPostCount = getPostCountByCategory(Category.PostCategory.OCEANIA);
//        Long americaPostCount = getPostCountByCategory(Category.PostCategory.AMERICA);
//
//        List<HomeCountryCountDto> countryCounts = new ArrayList<>();
//        countryCounts.add(new HomeCountryCountDto(
//                Category.PostCategory.ASIA.getValue(),
//                asiaPostCount,
//                africaPostCount,
//                europePostCount,
//                oceaniaPostCount,
//                americaPostCount
//        ));
//
//        countryCounts.add(new HomeCountryCountDto(
//                Category.PostCategory.AFRICA.getValue(),
//                asiaPostCount,
//                africaPostCount,
//                europePostCount,
//                oceaniaPostCount,
//                americaPostCount
//        ));
//
//        countryCounts.add(new HomeCountryCountDto(
//                Category.PostCategory.EUROPE.getValue(),
//                asiaPostCount,
//                africaPostCount,
//                europePostCount,
//                oceaniaPostCount,
//                americaPostCount
//        ));
//
//        countryCounts.add(new HomeCountryCountDto(
//                Category.PostCategory.OCEANIA.getValue(),
//                asiaPostCount,
//                africaPostCount,
//                europePostCount,
//                oceaniaPostCount,
//                americaPostCount
//        ));
//
//        countryCounts.add(new HomeCountryCountDto(
//                Category.PostCategory.AMERICA.getValue(),
//                asiaPostCount,
//                africaPostCount,
//                europePostCount,
//                oceaniaPostCount,
//                americaPostCount
//        ));
//
//        return countryCounts;
//
//
//    private Long getPostCountByCategory(Category.PostCategory category) {
//        return jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(category))
//                .fetchOne();
//    }
//}
//        Map<String, Long> countryCounts = new LinkedHashMap<>();
//
//        Long asiaPostCount = getPostCountByCategory(Category.PostCategory.ASIA);
//        Long africaPostCount = getPostCountByCategory(Category.PostCategory.AFRICA);
//        Long europePostCount = getPostCountByCategory(Category.PostCategory.EUROPE);
//        Long oceaniaPostCount = getPostCountByCategory(Category.PostCategory.OCEANIA);
//        Long americaPostCount = getPostCountByCategory(Category.PostCategory.AMERICA);
//
//        countryCounts.put("asiaPostCount", asiaPostCount);
//        countryCounts.put("africaPostCount", africaPostCount);
//        countryCounts.put("europePostCount", europePostCount);
//        countryCounts.put("oceaniaPostCount", oceaniaPostCount);
//        countryCounts.put("americaPostCount", americaPostCount);
//
//        return countryCounts;
//    }
//
//    private Long getPostCountByCategory(Category.PostCategory category) {
//        return jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(category))
//                .fetchOne();
//    }
//}

//        Long asiaPostCount = getPostCountByCategory(Category.PostCategory.ASIA);
//        Long africaPostCount = getPostCountByCategory(Category.PostCategory.AFRICA);
//        Long europePostCount = getPostCountByCategory(Category.PostCategory.EUROPE);
//        Long oceaniaPostCount = getPostCountByCategory(Category.PostCategory.OCEANIA);
//        Long americaPostCount = getPostCountByCategory(Category.PostCategory.AMERICA);
//
//        HomeCountryCountDto countryCountDto = new HomeCountryCountDto(asiaPostCount, africaPostCount, europePostCount, oceaniaPostCount, americaPostCount);
//
//        return Collections.singletonList(countryCountDto);
//    }
//
//    private Long getPostCountByCategory(Category.PostCategory category) {
//        return jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(category))
//                .fetchOne();
//    }
//



//        QPost post = QPost.post;
//
//        Long ASIAPostCount = jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(Category.PostCategory.ASIA))
//                .fetchOne();
//
//        Long AFRICAPostCount = jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(Category.PostCategory.AFRICA))
//                .fetchOne();
//
//        Long EUROPEPostCount = jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(Category.PostCategory.EUROPE))
//                .fetchOne();
//
//        Long OCEANIAPostCount = jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(Category.PostCategory.OCEANIA))
//                .fetchOne();
//
//        Long AMERICAPostCount = jpaQueryFactory
//                .select(post.id.count())
//                .from(post)
//                .where(post.category.eq(Category.PostCategory.AMERICA))
//                .fetchOne();
//
//        return Collections.singletonList(new HomeCountryCountDto(
//                ASIAPostCount,
//                AFRICAPostCount,
//                EUROPEPostCount,
//                OCEANIAPostCount,
//                AMERICAPostCount
//        ));

//        return Collections.singletonList(new HomeCountryCountDto(ASIAPostCount, AFRICAPostCount, EUROPEPostCount, OCEANIAPostCount, AMERICAPostCount));




