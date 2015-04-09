package org.smarty.core.support;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.bean.TemplateConfig;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.LilystudioUtil;
import org.smarty.core.utils.LogicUtil;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 邮件管理
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MailManager implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(MailManager.class);
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

    public void sendMail(String subject, String context, String[] toMail) {
        try {
            SystemConfig systemConfig = getSystemConfig();
            MimeMessage mm = createMailSender();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mm, false, "utf-8");
            mimeMessageHelper.setFrom(MimeUtility.encodeWord(systemConfig.getSystemName()) + " <" + systemConfig.getSmtpFromMail() + ">");
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(context, true);
            addSendMailTask(mm);
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

    public MimeMessage createMailSender() {
        SystemConfig systemConfig = getSystemConfig();
        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
        javaMailSenderImpl.setHost(systemConfig.getSmtpHost());
        javaMailSenderImpl.setPort(systemConfig.getSmtpPort());
        javaMailSenderImpl.setUsername(systemConfig.getSmtpUsername());
        javaMailSenderImpl.setPassword(systemConfig.getSmtpPassword());
        return javaMailSenderImpl.createMimeMessage();
    }

    public String createMailContext(String file, Map<String, Object> data) throws IOException {
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TemplateConfig config = new TemplateConfig();
        config.setSrc(is);
        config.setTarget(baos);
        LilystudioUtil.render(config, data);
        is.close();
        return baos.toString();
    }

    private SystemConfig getSystemConfig() {
        return SystemConfigUtil.getSystemConfig();
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
