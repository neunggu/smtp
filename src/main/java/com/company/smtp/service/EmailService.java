package com.company.smtp.service;

import com.company.smtp.model.EmailDto;
import com.company.smtp.util.EmailConsts;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Configuration freeMarkerConfiguration;

    @Async
    public void sendEmailWithTemplate(EmailDto email){
        Map<String, Object> model = getTemplateContents(email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setText(getTemplate(model), true);
            // it takes 13s
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }  catch (Exception e) {
            log.error(e.toString());
        }
    }

    private String getTemplate(Map<String, Object> model) throws IOException, TemplateException {
        StringBuffer content = new StringBuffer();
        content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                freeMarkerConfiguration.getTemplate(EmailConsts.TEMPLATE_PATH), model));
        return content.toString();
    }

    private Map<String, Object> getTemplateContents(EmailDto email) {
        Map<String, Object> model = new HashMap<>();
        model.put("lang", email.getLang());
        model.put("template", email.getTemplate().getTemplateName());
        switch (email.getTemplate()) {
            case VERIFICATION:
                model.put("verificationNumber", email.getText());
                break;
            case WELCOME:
                model.put("contents", email.getText());
                break;
            case INACTIVE:
                model.put("userName", email.getText());
                model.put("userEmail", email.getTo());
                model.put("inactiveDate", LocalDate.now().plusMonths(1));
                break;
            default:
                break;
        }
        return model;
    }
}
