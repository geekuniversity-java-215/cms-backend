package com.github.geekuniversity_java_215.cmsbackend.mail.services;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    String buildPaymentSuccess(String clientId) {
        Context context = new Context();
        context.setVariable("order", "order");
        return templateEngine.process("mail/payment_success", context);
    }

    
    //todo на вход buildRegConfirmationEmail необходимо передавать сущность Клиента, который регистрируется
    String buildRegistrationConfirmationEmail(Person person, String url) {
        Context context = new Context();
        context.setVariable("user", person.getLastName() + " " + person.getFirstName());
        context.setVariable("reg_url", url);
        context.setVariable("user_confirm_mail", "Завершить регистрацию");
        return templateEngine.process("mail/reg_confirmation-mail", context);
    }



}

