package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/paypal")
@Slf4j
public class PaymentController {

    private final PayPalService payPalService;

    @Autowired
    public PaymentController(PayPalService payPalService ) {
        this.payPalService=payPalService;
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

    @PostMapping("/payment")
    private String executePayment(@RequestParam(name = "clientId") String clientId,
                                  @RequestParam(name = "amount") Integer amount) throws PayPalRESTException {
        log.info("получен запрос на payment");
        String approvalLink = payPalService.authorizePayment(clientId,amount);
        log.info("Ответ запрос на authorize_payment="+approvalLink);
        return "redirect:" + approvalLink;
    }

/*
Отрабатывается успешное подтверждение перевода с кошелька клиента на базовый кошелек CMS
#paymentId - ID платежа
#PayerID - кто оплачивал
#result - подтверждение платежа
 */
    @GetMapping("/success/{clientId}")
    public void success(@RequestParam(name = "paymentId") String paymentId,
                          @RequestParam(name = "PayerID") String payerId,
                          Model model) throws PayPalRESTException {

        String result;
        result=payPalService.executePayment(paymentId,payerId);

        model.addAttribute("result",result);
        //todo здесь нужен возврат на страницу, подтверждающую что платеж выполнен
        //return "paypal_result";
    }
}
