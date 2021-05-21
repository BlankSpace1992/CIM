package com.yjh.web.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author yujunhong
 * @date 2021/5/21 10:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMailVO {
    /**
     * 邮件 ID
     */
    private String id;

    /**
     * 邮件发送人
     */
    private String from;

    /**
     * 邮件接收人
     */
    private String to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String text;
    /**
     * 邮件发送时间
     */
    private Date sendDate;
    /**
     * 邮件抄送
     */
    private String cc;
    /**
     * 邮件密送
     */
    private String bcc;
    /**
     * 邮件发送状态
     */
    private String status;
    /**
     * 邮件发送错误信息
     */
    private String error;
    /**
     * 邮件附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;
}
