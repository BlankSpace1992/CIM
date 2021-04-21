package com.yjh.web.blog.controller;

import com.yjh.vo.BlogQuery;
import com.yjh.web.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 首页 Controller
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
public class IndexController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IWebsiteInfoService websiteInfoService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,
                        HttpSession session,
                        HttpServletRequest httpServletRequest) {
        model.addAttribute("page", blogService.listBlogs(pageable, new BlogQuery()));
        model.addAttribute("types", typeService.listTypes());
        model.addAttribute("tags", tagService.listTags());
        model.addAttribute("recommendBlogs", blogService.listBlogsByRecommend(20,
                BlogQuery.builder().recommend("1").build()));
        session.setAttribute("views", websiteInfoService.addOneForViews());
        session.setAttribute("aboutMeImageUrl", websiteInfoService.getAboutMeImageUrl());
        session.setAttribute("topTitle", websiteInfoService.getTopTitle());
        session.setAttribute("aboutMeContent", websiteInfoService.getAboutMeContent());

        //网站访问记录
        recordService.recording(httpServletRequest);

        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlogs(pageable, BlogQuery.builder().query(query).build()));
        model.addAttribute("query", query);
        model.addAttribute("newblogs", blogService.listBlogsByRecommend(5,
                BlogQuery.builder().recommend("1").build()));
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listBlogsByRecommend(6,
                BlogQuery.builder().recommend("1").build()));
        return "_fragments :: newblogList";
    }

}
