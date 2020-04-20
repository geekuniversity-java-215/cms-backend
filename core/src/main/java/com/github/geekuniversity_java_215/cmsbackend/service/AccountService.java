package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import com.github.geekuniversity_java_215.cmsbackend.repository.AccountRepository;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AccountService extends BaseService<Account> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }


    /**
     * Зачислить на счет
     * @param account
     * @param amount
     */
    public void addBalance(Account account, BigDecimal amount) throws InterruptedException {

        log.info("Хотип пополнить баланс, id={}", account.getId());

        accountRepository.lockByAccount(account);

        // TESTING
        log.info("Усиленно работаем ...");
        TimeUnit.SECONDS.sleep(5);

        log.info("Пополняем баланс, id={}", account.getId());
        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        log.info("Снимаем блокировку строки, id={}", account.getId());
    }

    /**
     * Снять со счета
     * @param account
     * @param amount
     */
    public void removeBalance(Account account, BigDecimal amount) {

        accountRepository.lockByAccount(account);
        account.getBalance().subtract(amount);
        accountRepository.save(account);
    }

}
