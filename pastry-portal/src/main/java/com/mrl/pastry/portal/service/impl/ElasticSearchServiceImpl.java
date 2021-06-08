package com.mrl.pastry.portal.service.impl;

//import com.mrl.pastry.common.utils.SecurityUtils;
//import com.mrl.pastry.portal.model.dto.UserBaseDTO;
//import com.mrl.pastry.portal.model.entity.Blog;
//import com.mrl.pastry.portal.model.es.BlogDocument;
//import com.mrl.pastry.portal.model.es.UserDocument;
//import com.mrl.pastry.portal.model.vo.BlogVO;
//import com.mrl.pastry.portal.repository.BlogRepository;
//import com.mrl.pastry.portal.repository.UserRepository;
//import com.mrl.pastry.portal.service.BlogService;
//import com.mrl.pastry.portal.service.ElasticSearchService;
//import com.mrl.pastry.portal.service.UserService;
//import io.jsonwebtoken.lang.Assert;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static com.mrl.pastry.portal.constant.DefaultConstant.POST_TAG;
//import static com.mrl.pastry.portal.constant.DefaultConstant.PRE_TAG;
//
///**
// * ElasticSearchService implementation
// *
// * @author MrL
// * @version 1.0
// * @date 2021/4/27
// */
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ElasticSearchServiceImpl implements ElasticSearchService {
//
//    private final ElasticsearchRestTemplate restTemplate;
//
//    private final BlogRepository blogIndexRepository;
//    private final UserRepository userIndexRepository;
//
//    private final BlogService blogService;
//    private final UserService userService;
//
//    @Override
//    public Page<BlogVO> searchBlogs(@NonNull String keyword, Pageable pageable) {
//        Assert.notNull(keyword, "keyword must not be null");
//
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        // set page query
//        builder.withPageable(pageable);
//        if (StringUtils.isEmpty(keyword)) {
//            // match all
//            builder.withQuery(QueryBuilders.matchAllQuery());
//        } else {
//            // set query fields and highlight
//            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content", "summary"));
//            HighlightBuilder highlightBuilder = new HighlightBuilder().field("title").field("content").field("summary")
//                    .preTags(PRE_TAG).postTags(POST_TAG);
//            builder.withHighlightBuilder(highlightBuilder);
//        }
//        // TODO: 分类、tag聚合
//        NativeSearchQuery searchQuery = builder.build();
//        log.debug("search query: [{}]", searchQuery.getQuery());
//        // resolve
//        final SearchHits<BlogDocument> searchHits = restTemplate.search(searchQuery, BlogDocument.class);
//        List<BlogDocument> records = searchHits.stream().map(hit -> {
//            BlogDocument doc = hit.getContent();
//            Map<String, List<String>> highlightFields = hit.getHighlightFields();
//            List<String> content = highlightFields.get("content");
//            if (!CollectionUtils.isEmpty(content)) {
//                doc.setContent(content.get(0));
//            }
//            // TODO:title、summary
//            return doc;
//        }).collect(Collectors.toList());
//        return new PageImpl<>(records, pageable, records.size()).map(doc -> {
//            Long userId = SecurityUtils.getCurrentUserId();
//            BlogVO blogVO = blogService.convertToBlogVO(new Blog(doc.getId(), doc.getAuthor()), userId);
//            // replace with highlighted content
//            blogVO.setContent(doc.getContent());
//            return blogVO;
//        });
//    }
//
//    @Override
//    public Page<UserBaseDTO> searchUsers(@NonNull String name, Pageable pageable) {
//        Assert.notNull(name, "keyword must not be null");
//
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        // set page query
//        builder.withPageable(pageable);
//        if (StringUtils.isEmpty(name)) {
//            // match all
//            builder.withQuery(QueryBuilders.matchAllQuery());
//        } else {
//            builder.withQuery(QueryBuilders.matchQuery("nickname", name));
//        }
//        NativeSearchQuery query = builder.build();
//        log.debug("search user: [{}]", query.getQuery());
//
//        SearchHits<UserDocument> searchHits = restTemplate.search(query, UserDocument.class);
//        List<UserDocument> records = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
//        return new PageImpl<>(records, pageable, records.size()).map(doc -> userService.getUserDto(doc.getId()));
//    }
//}
