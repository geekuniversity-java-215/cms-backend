package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import com.github.geekuniversity_java_215.cmsbackend.payment.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class TransactionService extends BaseRepoAccessService<Transaction> {
    private final static String REQUEST="request";
    private final static String DEPOSIT="deposit";

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,UserService userService){
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public Transaction addRequestForFunds(Long userId, BigDecimal amount, String currencyCodeType){
        Optional<User> user=userService.findById(userId);
        Transaction transaction=new Transaction(user.get(),REQUEST,amount,currencyCodeType);
        transactionRepository.save(transaction);
        log.info("Записана транзакция"+transaction.toString());
        return transaction;

    }

}
