package com.yjh.web.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页 Controller
 *
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
public class IndexController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

}
