package com.yjh.web.blog.controller;

import com.yjh.web.blog.domain.Tag;
import com.yjh.web.blog.service.IBlogService;
import com.yjh.web.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/** 标签展示 Controller
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
public class TagShowController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTags();
        if (id == -1) {
           id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlogsByTagId(pageable,id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
