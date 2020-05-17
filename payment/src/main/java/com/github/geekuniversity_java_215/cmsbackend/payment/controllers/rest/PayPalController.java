package com.github.geekuniversity_java_215.cmsbackend.payment.controllers.rest;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalService;
import com.github.geekuniversity_java_215.cmsbackend.utils.interfaces.AuthenticationFacade;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;

import static com.github.geekuniversity_java_215.cmsbackend.payment.data.constants.PaymentPropNames.*;

@RestController
@Slf4j
public class PayPalController {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final PayPalService payPalService;

    public PayPalController(AuthenticationFacade authenticationFacade,
                            UserService userService,
                            PayPalService payPalService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.payPalService = payPalService;
    }

    /*
   Отрабатывается успешное подтверждение перевода с кошелька клиента на базовый кошелек CMS
   #paymentId - ID платежа
   #PayerID - кто оплачивал
   #result - подтверждение платежа
    */
    @GetMapping(CONTROLLER_SUCCESS_PATH)
    public ResponseEntity<?> success(@RequestParam(name = "paymentId") String paymentId,
                                     @RequestParam(name = "PayerID") String payerId,
                                     @PathVariable Long clientId) {

        ResponseEntity<?> result;

        try {

            User user = userService.getCurrentAuthenticatedUser();

            if (user.getId().equals(clientId)) {
                String paymentResult;
                log.info("payerId: {}, paymentId: {}", payerId, paymentId);
                paymentResult = payPalService.executePayment(paymentId, payerId, user);
                log.info("paymentResult: {}", paymentResult);
                //model.addAttribute("result", result);
            } else {

                String err = "Id авторизованного пользователя не совпадает с id инициатора платежа. Id авторизованного == " + user.getId() + ". Платеж инициировал id == " + clientId;
                // FixMe: implement InvalidLogicException in core
                throw new IllegalArgumentException(err);

                //model.addAttribute("result", "id авторизованного пользователя не совпадает с id инициатора платежа");
            }

            //todo здесь нужен возврат на страницу, подтверждающую что платеж выполнен.
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("https://natribu.org/"));
            result = new ResponseEntity<>(headers, HttpStatus.FOUND);

        }
        catch (IllegalArgumentException e) {
            log.error("Payment validation error", e);
            String message = "User validation error: " + e.getMessage();
            result = ResponseEntity.badRequest().body(message);
        }
        catch (Exception e) {
            log.error("payment error", e);
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    //ToDo implement this
    @GetMapping(CONTROLLER_CANCEL_PATH)
    public ResponseEntity<?> cancel(@RequestParam(name = "paymentId") String paymentId,
                                    @RequestParam(name = "PayerID") String payerId,
                                    @PathVariable Long clientId) {

        return ResponseEntity.ok().body("8989");
    }


}
