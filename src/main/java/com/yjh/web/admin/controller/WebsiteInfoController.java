package com.yjh.web.admin.controller;

import com.yjh.web.blog.service.IWebsiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 网站抬头 Controller
 *
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
public class WebsiteInfoController {

    @Autowired
    private IWebsiteInfoService websiteInfoService;

    @GetMapping("/admin/websiteInfo")
    public String toWebsiteInfoPage(HttpSession session) {
        String aboutMeImageUrl = websiteInfoService.getAboutMeImageUrl();
        String topTitle = websiteInfoService.getTopTitle();
        session.setAttribute("topTitle", topTitle);
        session.setAttribute("aboutMeImageUrl", aboutMeImageUrl);
        return "admin/websiteInfo";
    }

    @PostMapping("/admin/updateAboutMeImageUrl")
    public String updateAboutMeImageUrl(String aboutMeImageUrl, HttpSession session) {
        String s = websiteInfoService.updateAboutMeImageUrl(aboutMeImageUrl);
        session.setAttribute("aboutMeImageUrl", s);
        return "/admin/websiteInfo";
    }

    @PostMapping("/admin/updateTopTitle")
    public String updateTopTitle(String topTitle, HttpSession session) {
        String s = websiteInfoService.updateTopTitle(topTitle);
        session.setAttribute("topTitle", s);
        return "/admin/websiteInfo";
    }
}
