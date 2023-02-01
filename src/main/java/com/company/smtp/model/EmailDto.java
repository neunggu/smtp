package com.company.smtp.model;

import com.company.smtp.util.EmailConsts;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class EmailDto {

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String to;
    private String subject;
    private String text;
    private EmailConsts.Template template;
    private String lang;

    public EmailDto(String to, String subject, String text, EmailConsts.Template template, String lang) {
        this.to = to;
        this.subject = ObjectUtils.isEmpty(subject) ? template.getSubject(lang): subject;
        this.text = text;
        this.template = template;
        this.lang = ObjectUtils.isEmpty(lang) ? "ko": lang;
    }

}
