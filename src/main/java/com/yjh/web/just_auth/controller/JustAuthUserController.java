package com.yjh.web.just_auth.controller;

import com.alibaba.fastjson.JSON;
import com.xkcoding.justauth.AuthRequestFactory;
import com.yjh.common.exception.ResultBody;
import com.yjh.util.StringUtils;
import com.yjh.web.just_auth.domain.JustAuthUser;
import com.yjh.web.just_auth.service.JustAuthUserService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author yujunhong
 * @date 2021/5/19 10:33
 */
@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class JustAuthUserController {
    @Autowired
    private AuthRequestFactory authRequestFactory;

    @Autowired
    private JustAuthUserService justAuthUserService;

    /**
     * 登录
     *
     * @param type     第三方系统类型，例如：gitee/baidu
     * @param response 响应实体
     * @author yujunhong
     * @date 2021/5/19 10:36
     */
    @GetMapping(value = "/login/{type}")
    public void login(@PathVariable(value = "type") String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = authRequestFactory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * 登录回调
     *
     * @param type         第三方系统类型，例如：gitee/baidu
     * @param authCallback 回调实体
     * @return 响应实体
     * @author yujunhong
     * @date 2021/5/19 10:39
     */
    @SuppressWarnings(value = "unchecked")
    @GetMapping(value = "/{type}/callback")
    public String login(@PathVariable(value = "type") String type, AuthCallback authCallback, HttpSession session) {
        AuthRequest authRequest = authRequestFactory.get(type);
        AuthResponse<JustAuthUser> response = authRequest.login(authCallback);
        log.info("【response】= {}", JSON.toJSONString(response));
        if (response.ok()) {
            JustAuthUser justAuthUser = new JustAuthUser(response.getData());
            justAuthUserService.saveOrUpdateAuth(justAuthUser);
            session.setAttribute("justAuthUser", justAuthUser);
            return "redirect:/";
        }
        return "redirect:/";
    }

    /**
     * 收回授权
     *
     * @return 响应实体
     * @author yujunhong
     * @date 2021/5/19 10:45
     */
    @SuppressWarnings(value = "unchecked")
    @GetMapping(value = "/revoke")
    public String revoke(HttpSession session) {
        JustAuthUser authUser = (JustAuthUser) session.getAttribute("justAuthUser");
        // 类型
        String type = authUser.getSource();
        // uuid
        String uuid = authUser.getUuid();
        session.removeAttribute("justAuthUser");
        AuthRequest authRequest = authRequestFactory.get(type);
        JustAuthUser justAuthUser = justAuthUserService.getByUuid(uuid);
        if (StringUtils.isNull(justAuthUser)) {
            return "redirect:/";
        }
        AuthResponse<AuthToken> response = null;
        try {
            response = authRequest.revoke(justAuthUser.getToken());
            if (response.ok()) {
                justAuthUserService.removeByUuid(justAuthUser.getUuid());
                return "redirect:/";
            }
            return "redirect:/";
        } catch (AuthException e) {
            log.error(e.getErrorMsg());
            return "redirect:/";
        }
    }

    /**
     * 刷新
     *
     * @param type 第三方系统类型，例如：gitee/baidu
     * @param uuid 用户uuid
     * @return 响应实体
     * @author yujunhong
     * @date 2021/5/19 10:45
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/refresh/{type}/{uuid}")
    @ResponseBody
    public ResultBody refresh(@PathVariable String type, @PathVariable String uuid) {
        AuthRequest authRequest = authRequestFactory.get(type);

        JustAuthUser justAuthUser = justAuthUserService.getByUuid(uuid);
        if (StringUtils.isNull(justAuthUser)) {
            return ResultBody.error("用户不存在");
        }

        AuthResponse<AuthToken> response = null;
        try {
            response = authRequest.refresh(justAuthUser.getToken());
            if (response.ok()) {
                justAuthUser.setToken(response.getData());
                justAuthUserService.saveOrUpdate(justAuthUser);
                return ResultBody.success("用户 [" + justAuthUser.getUsername() + "] 的 access token 已刷新！新的 accessToken: "
                        + response.getData().getAccessToken());
            }
            return ResultBody.error("用户 [" + justAuthUser.getUsername() + "] 的 access token 刷新失败！" + response.getMsg());
        } catch (AuthException e) {
            return ResultBody.error(e.getErrorMsg());
        }
    }

}
