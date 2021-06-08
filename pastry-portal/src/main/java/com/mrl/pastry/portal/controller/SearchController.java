package com.mrl.pastry.portal.controller;

//import com.mrl.pastry.portal.model.dto.UserBaseDTO;
//import com.mrl.pastry.portal.model.vo.BlogVO;
//import com.mrl.pastry.portal.service.ElasticSearchService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import static org.springframework.data.domain.Sort.Direction.DESC;
//
///**
// * Search controller
// *
// * @author MrL
// * @version 1.0
// * @date 2021/4/26
// */
//@RestController
//@RequestMapping("/search")
//public class SearchController {
//
//    private final ElasticSearchService service;
//
//    public SearchController(ElasticSearchService service) {
//        this.service = service;
//    }
//
//    @GetMapping("/blog")
//    @ApiOperation("Search blogs with keyword")
//    public Page<BlogVO> searchBlog(@PageableDefault(sort = {"id"}, direction = DESC) Pageable pageable,
//                                   @RequestParam String keyword) {
//        return service.searchBlogs(keyword, pageable);
//    }
//
//    @GetMapping("/user")
//    @ApiOperation("Search users with name")
//    public Page<UserBaseDTO> searchUser(@PageableDefault(sort = {"id"}, direction = DESC) Pageable pageable,
//                                        @RequestParam String name) {
//        return service.searchUsers(name, pageable);
//    }
//}
