package com.yjh.web.mail.service.impl;

import com.yjh.common.exception.ResultBody;
import com.yjh.common.exception.enums.BaseErrorCommonEnum;
import com.yjh.util.StringUtils;
import com.yjh.web.mail.domain.SendMailVO;
import com.yjh.web.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

/**
 * @author yujunhong
 * @date 2021/5/21 11:29
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public SendMailVO sendMail(SendMailVO sendMailVO) {
        // 获取发送人信息 从配置文件获取
        String from = mailSender.getJavaMailProperties().getProperty("from");
        sendMailVO.setFrom(from);
        // 检查邮件是否合法
        ResultBody resultBody = checkMail(sendMailVO);
        if (!resultBody.getCode().equals(BaseErrorCommonEnum.SUCCESS.getErrorCode())) {
            sendMailVO.setStatus("Fail");
            sendMailVO.setError(resultBody.getMessage());
        }
        // **********发送邮件处理******************
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            // 邮件接收人人
            mimeMessageHelper.setTo(sendMailVO.getTo());
            // 邮件发送人
            mimeMessageHelper.setFrom(sendMailVO.getFrom());
            // 邮件主题
            mimeMessageHelper.setSubject(sendMailVO.getSubject());
            // 邮件内容
            mimeMessageHelper.setText(sendMailVO.getText());
            // 处理抄送
            if (StringUtils.isNotEmpty(sendMailVO.getCc())) {
                mimeMessageHelper.setCc(sendMailVO.getCc().split(","));
            }
            // 处理密送
            if (StringUtils.isNotEmpty(sendMailVO.getBcc())) {
                mimeMessageHelper.setBcc(sendMailVO.getBcc().split(","));
            }
            // 处理邮件附件
            if (StringUtils.isNotNull(sendMailVO.getMultipartFiles())) {
                for (MultipartFile multipartFile : sendMailVO.getMultipartFiles()) {
                    mimeMessageHelper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()),
                            multipartFile);
                }
            }
            // 处理发送时间
            if (StringUtils.isNull(sendMailVO.getSendDate())) {
                sendMailVO.setSendDate(new Date());
                mimeMessageHelper.setSentDate(sendMailVO.getSendDate());
            }
            // 发送信息
            mailSender.send(mimeMessageHelper.getMimeMessage());
            sendMailVO.setStatus("OK");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return sendMailVO;
    }

    /**
     * 检查邮件是否合法
     *
     * @param sendMailVO 邮件实体
     * @author yujunhong
     * @date 2021/5/21 11:35
     */
    private ResultBody checkMail(SendMailVO sendMailVO) {
        if (StringUtils.isEmpty(sendMailVO.getFrom())) {
            return ResultBody.error("邮件发送人不能为空");
        }
        if (StringUtils.isEmpty(sendMailVO.getTo())) {
            return ResultBody.error("邮件接收人不能为空");
        }
        if (StringUtils.isEmpty(sendMailVO.getSubject())) {
            return ResultBody.error("邮件主题不能为空");
        }
        return ResultBody.success();
    }
}
