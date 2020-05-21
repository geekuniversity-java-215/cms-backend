package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.payment.configurations.PayPalAccount;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ReceiverInfoCodeType;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PayPalMassPaymentsService {
    private final PayPalAccount payPalAccount;

    @Autowired
    public PayPalMassPaymentsService(PayPalAccount payPalAccount) {
        this.payPalAccount = payPalAccount;
    }
    //todo создать сущность(журнал) вывода средств RequestForFunds с атрибутами(userId,amount,currencyCodeType,date_create,date_success).
    //todo создать контроллер, который в автоматическом режиме 3 раза в неделю будет проверять RequestForFunds на наличие неввыполненных заявок
    // и передавать их списков в doMassPayments, до 250 шт в одной пачке

    public String doMassPayment(String userMail,String amount, String currencyCodeType) {
        MassPayReq massPayReq = new MassPayReq();
        List<MassPayRequestItemType> massPayRequestItemTypeList = new ArrayList<>();
        requestItemList(userMail, amount, currencyCodeType, massPayRequestItemTypeList);

        massPayReq.setMassPayRequest(getRequestType(massPayRequestItemTypeList));

        // Creating service wrapper object to make an API call by loading configuration map.
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap());

        try {
            MassPayResponseType resp = service.massPay(massPayReq);
            log.info(resp.getAck().getValue());
            return "success";
        } catch (SSLConfigurationException e) {
            e.printStackTrace();
        } catch (InvalidCredentialException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpErrorException e) {
            e.printStackTrace();
        } catch (InvalidResponseDataException e) {
            e.printStackTrace();
        } catch (ClientActionRequiredException e) {
            e.printStackTrace();
        } catch (MissingCredentialException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    /*
 *  (Optional) How you identify the recipients of payments in this call to MassPay.
 *   It is one of the following values:
        EmailAddress
        UserID
        PhoneNumber
 */
    private MassPayRequestType getRequestType(List<MassPayRequestItemType> massPayRequestItemTypeList) {
        MassPayRequestType reqType = new MassPayRequestType(massPayRequestItemTypeList);
        reqType.setReceiverType(ReceiverInfoCodeType.fromValue("EmailAddress"));
        return reqType;
    }

    private void requestItemList(String userMail, String amount, String currencyCodeType, List<MassPayRequestItemType> massPayRequestItemTypeList) {
        BasicAmountType basicAmountType = new BasicAmountType(
                CurrencyCodeType.fromValue(currencyCodeType),
                amount);

        MassPayRequestItemType item1 = new MassPayRequestItemType(basicAmountType);
        item1.setReceiverEmail(userMail);

        massPayRequestItemTypeList.add(item1);
    }

    private Map<String,String> configurationMap() {
        final Map<String, String> configurationMap = new HashMap<>();
        configurationMap.put("mode", payPalAccount.getMode());
        configurationMap.put("acct1.UserName", payPalAccount.getUsername());
        configurationMap.put("acct1.Password", payPalAccount.getPassword());
        configurationMap.put("acct1.Signature", payPalAccount.getSignature());
        return configurationMap;
    }

}
