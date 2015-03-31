package org.smarty.web.commons;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.LogicUtil;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;

/**
 * 发送邮件工具
 */
public class MailManager implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(MailManager.class);
    private FreemarkerManager freemarkerManager;
    private JavaMailSender javaMailSender;
    private TaskExecutor taskExecutor;


    public void afterPropertiesSet() throws Exception {
    }

    public boolean isMailConfigComplete() {
        SystemConfig systemConfig = getSystemConfig();
        boolean boo = LogicUtil.isNotEmpty(systemConfig.getSmtpFromMail());
        boo = boo && LogicUtil.isNotEmpty(systemConfig.getSmtpHost());
        boo = boo && systemConfig.getSmtpPort() == null;
        boo = boo && LogicUtil.isNotEmpty(systemConfig.getSmtpUsername());
        boo = boo && LogicUtil.isNotEmpty(systemConfig.getSmtpPassword());
        return boo;
    }

    public void sendMail(String subject, String templateFilePath, Map<String, Object> data, String[] toMail) {
        try {
            SystemConfig systemConfig = getSystemConfig();

            JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
            javaMailSenderImpl.setHost(systemConfig.getSmtpHost());
            javaMailSenderImpl.setPort(systemConfig.getSmtpPort());
            javaMailSenderImpl.setUsername(systemConfig.getSmtpUsername());
            javaMailSenderImpl.setPassword(systemConfig.getSmtpPassword());
            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();

            String text = freemarkerManager.getTemplateString(templateFilePath, data);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setFrom(MimeUtility.encodeWord(systemConfig.getSystemName()) + " <" + systemConfig.getSmtpFromMail() + ">");
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            addSendMailTask(mimeMessage);
        } catch (Exception e) {
            logger.out(e);
        }
    }

    // 增加邮件发送任务
    public void addSendMailTask(final MimeMessage mimeMessage) {
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    javaMailSender.send(mimeMessage);
                }
            });
        } catch (Exception e) {
            logger.out(e);
        }
    }

    private SystemConfig getSystemConfig() {
        return SystemConfigUtil.getSystemConfig();
    }

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
