package com.github.geekuniversity_java_215.cmsbackend.services.impl;

import com.github.geekuniversity_java_215.cmsbackend.services.MailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailMessageBuilderImpl implements MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    public String buildPaymentSuccess(String clientId) {
        Context context = new Context();
        context.setVariable("order", "order");
        return templateEngine.process("mail/payment_success", context);
    }

    public String buildRegConfirmationEmail(String clientId) {
        Context context = new Context();
        context.setVariable("user", "user");
        context.setVariable("reg_url","url");
        return templateEngine.process("mail/reg_confirmation-mail", context);

    }



}
