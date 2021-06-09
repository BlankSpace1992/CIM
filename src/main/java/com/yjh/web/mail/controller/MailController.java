package com.yjh.web.mail.controller;

import com.yjh.common.exception.ResultBody;
import com.yjh.web.mail.domain.SendMailVO;
import com.yjh.web.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yujunhong
 * @date 2021/5/21 13:39
 */
@RestController
@RequestMapping(value = "/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    /**
     * @param sendMailVO 邮件实体
     * @return 成功/失败
     * @author yujunhong
     * @date 2021/5/21 13:41
     */
    @PostMapping(value = "/send")
    public ResultBody sendMail(@RequestBody SendMailVO sendMailVO) {
        return ResultBody.success(mailService.sendMail(sendMailVO));
    }

}
