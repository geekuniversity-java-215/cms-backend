package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.payment.configurations.PayPalAccount;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ReceiverInfoCodeType;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

/*
schedulerPayment 4 раза в неделю запускает doMassPayment для выполнения заявок на вывод средств
paypal.cron.expression=0 58 23 * * 1,3,5,7
 */

@Service
@Slf4j
public class PayPalMassPaymentsService {
    private final PayPalAccount payPalAccount;
    private final CashFlowService cashFlowService;

    @Autowired
    public PayPalMassPaymentsService(PayPalAccount payPalAccount, CashFlowService cashFlowService) {
        this.payPalAccount = payPalAccount;
        this.cashFlowService = cashFlowService;
    }

    //todo передавать в doMassPayments до 250 шт в одном списке
    @Scheduled(cron ="${paypal.cron.expression}" )
    public void schedulerPayment(){
        doMassPayment(cashFlowService.findAllWithEmptyDateSuccess());
    }

    public void doMassPayment(List<CashFlow> transactionsList) {
        MassPayReq massPayReq = new MassPayReq();
        List<MassPayRequestItemType> massPayRequestItemTypeList;
        massPayRequestItemTypeList=requestItemList(transactionsList);
        massPayReq.setMassPayRequest(getRequestType(massPayRequestItemTypeList));

        // Creating service wrapper object to make an API call by loading configuration map.
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap());

        try {
            MassPayResponseType resp = service.massPay(massPayReq);
            log.info(resp.getAck().getValue());
            saveDateSuccess(massPayRequestItemTypeList);
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
    }

    private void saveDateSuccess(List<MassPayRequestItemType> massPayRequestItemTypeList) {
        Optional<CashFlow> cf;
        for (MassPayRequestItemType massPayRequestItemType: massPayRequestItemTypeList) {
            cf=cashFlowService.findById(Long.valueOf(massPayRequestItemType.getReceiverID()));
            cf.get().setDateSuccess(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            cashFlowService.save(cf.get());
        }
    }

    private MassPayRequestType getRequestType(List<MassPayRequestItemType> massPayRequestItemTypeList) {
        MassPayRequestType reqType = new MassPayRequestType(massPayRequestItemTypeList);
        reqType.setReceiverType(ReceiverInfoCodeType.fromValue("EmailAddress"));
        return reqType;
    }

    private List<MassPayRequestItemType> requestItemList(List<CashFlow> tr) {
        BasicAmountType basicAmountType;
        List<MassPayRequestItemType> massPayRequestItemTypeList=new ArrayList<>();
        for (CashFlow t: tr){
            basicAmountType=new BasicAmountType(CurrencyCodeType.fromValue(t.getCurrencyCodeType()), String.valueOf(t.getAmount()));
            MassPayRequestItemType ms=new MassPayRequestItemType(basicAmountType);
            ms.setReceiverEmail(t.getPayPalEmail());
            ms.setReceiverID(String.valueOf(t.getId()));
            massPayRequestItemTypeList.add(ms);
        }
        return massPayRequestItemTypeList;
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
