package com.yjh.web.blog.controller;

import com.yjh.web.blog.domain.LoginUser;
import com.yjh.web.blog.service.ILoginUserService;
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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginUserService loginUserService;

    @Autowired
    private IWebsiteInfoService websiteInfoService;

    @GetMapping
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        LoginUser loginUser = loginUserService.checkLoginUser(username, password);
        if (loginUser != null) {
            loginUser.setPassword(null);
            session.setAttribute("loginUser",loginUser);
            return "redirect:/";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }
}
