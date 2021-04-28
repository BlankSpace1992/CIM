package com.yjh.web.admin.controller;

import com.yjh.web.blog.domain.User;
import com.yjh.web.blog.service.IUserService;
import com.yjh.web.blog.service.IWebsiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/** 登录 Controller
 * @author yujunhong
 * @date 2021/4/21 15:13
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IWebsiteInfoService websiteInfoService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user);
            session.setAttribute("aboutMeImageUrl",websiteInfoService.getAboutMeImageUrl());
            session.setAttribute("topTitle",websiteInfoService.getTopTitle());
            session.setAttribute("topTitle",websiteInfoService.getTopTitle());
            session.setAttribute("aboutMeContent",websiteInfoService.getAboutMeContent());
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
