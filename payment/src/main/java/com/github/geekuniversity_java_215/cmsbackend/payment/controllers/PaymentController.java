package com.github.geekuniversity_java_215.cmsbackend.payment.controllers;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.github.geekuniversity_java_215.cmsbackend.payment.data.constants.PaymentPropNames.*;


@Controller
@RequestMapping("/paypal")
@Slf4j
public class PaymentController {

    private final PayPalService payPalService;
    private final UserService userService;

    @Autowired
    public PaymentController(PayPalService payPalService, UserService userService) {
        this.payPalService = payPalService;
        this.userService = userService;
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


    @PostMapping(CONTROLLER_EXECUTE_PAYMENT_PATH)
    private String executePayment(@RequestBody String amount) throws PayPalRESTException {
        User user = userService.getCurrentUser()
            .orElseThrow(() -> new UsernameNotFoundException("User " + UserService.getCurrentUsername() + " not found"));
        String approvalLink = payPalService.authorizePayment(String.valueOf(user.getId()), Integer.valueOf(amount));
        log.info("Ответ запрос на authorize_payment=" + approvalLink);
        return "redirect:" + approvalLink;
    }

    /*
    Отрабатывается успешное подтверждение перевода с кошелька клиента на базовый кошелек CMS
    #paymentId - ID платежа
    #PayerID - кто оплачивал
    #result - подтверждение платежа
     */
    @GetMapping(CONTROLLER_SUCCESS_PATH)
    public String success(@RequestParam(name = "paymentId") String paymentId,
                          @RequestParam(name = "PayerID") String payerId,
                          @PathVariable Long clientId,
                          Model model) throws PayPalRESTException {

        User user = userService.getCurrentUser()
            .orElseThrow(() -> new UsernameNotFoundException("User " + UserService.getCurrentUsername() + " not found"));

        if (user.getId().equals(clientId)) {
            String result = "";
            log.info("payerId:" + payerId);
            log.info("paymentId:" + paymentId);
            result = payPalService.executePayment(paymentId, payerId, user);
            log.info("result: " + result);
            model.addAttribute("result", result);
        }
        else{
            log.info("id авторизованного пользователя не совпадает с id инициатора платежа. id авторизованного="+user.getId()+". Платеж инициировал id="+clientId);
            model.addAttribute("result", "id авторизованного пользователя не совпадает с id инициатора платежа");
        }
        //todo здесь нужен возврат на страницу, подтверждающую что платеж выполнен. paypal_result это заглушка
        return "paypal_result";
    }

}
