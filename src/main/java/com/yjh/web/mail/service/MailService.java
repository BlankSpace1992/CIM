package com.yjh.web.mail.service;

import com.yjh.web.mail.domain.SendMailVO;

/**
 * @author yujunhong
 * @date 2021/5/21 11:28
 */
public interface MailService {
    /**
     * 发送邮件
     *
     * @param sendMailVO 发送邮件实体
     * @return 发送邮件信息
     * @author yujunhong
     * @date 2021/5/21 11:28
     */
    SendMailVO sendMail(SendMailVO sendMailVO);
}
