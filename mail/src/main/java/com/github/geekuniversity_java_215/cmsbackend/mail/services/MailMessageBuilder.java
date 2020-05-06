package com.github.geekuniversity_java_215.cmsbackend.mail.services;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;


@Service
public class MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    private MailMessageBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildPaymentSuccess(User user, BigDecimal amount ) {
        Context context = new Context();
        context.setVariable("sender", user.getFirstName());
        context.setVariable("amount",amount);
        context.setVariable("balance", user.getAccount().getBalance().toString());
        return templateEngine.process("mail/payment_success", context);
    }

    
    //на вход buildRegConfirmationEmail необходимо передавать сущность Клиента, который регистрируется
    public String buildRegistrationConfirmationEmail(User user, String url) {
        Context context = new Context();
        context.setVariable("user", user.getLastName() + " " + user.getFirstName());

        context.setVariable("reg_url", url);
        context.setVariable("user_confirm_mail", "Завершить регистрацию");
        return templateEngine.process("mail/reg_confirmation-mail", context);
    }



}

