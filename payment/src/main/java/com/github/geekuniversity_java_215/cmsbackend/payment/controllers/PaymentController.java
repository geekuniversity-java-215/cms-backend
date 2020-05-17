package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.payment.PaymentConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalService;
import com.github.geekuniversity_java_215.cmsbackend.utils.interfaces.AuthenticationFacade;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;


@JrpcController(HandlerName.payment.path)
@Slf4j
public class PaymentController {

    private final PayPalService payPalService;
    private final UserService userService;
    private final PaymentConverter paymentConverter;

    @Autowired
    public PaymentController(PayPalService payPalService,
                             UserService userService,
                             PaymentConverter paymentConverter) {
        this.payPalService = payPalService;
        this.userService = userService;
        this.paymentConverter = paymentConverter;
    }

    /*
    Отрабатывается Post-запрос на авторизацию платежа. Клиент должен будет зайти в свой PayPal кошелек
        - Поступает запрос на /payment
        - в методе authorizePayment происходит формирование платежа и сам перевод денег
        - при удачном завершении редирект на /success/{clientId}
        - отрабатывает метод executePayment для подтверждения платежной транзакции
    #clientId
    #amount - сумма платежа
     */


    @JrpcMethod(HandlerName.payment.execute)
    private JsonNode execute(JsonNode params) throws PayPalRESTException {

        User user = userService.getCurrentAuthenticatedUser();

        BigDecimal amount = paymentConverter.getAmount(params);

        String approvalLink = payPalService.authorizePayment(
            String.valueOf(user.getId()),
            amount);

        log.info("Ответ запрос на authorize_payment == " + approvalLink);

        return paymentConverter.toJson(approvalLink);
    }
}
