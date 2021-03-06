package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.AccountRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.aop.LogExecutionTime;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.services.currency.CurrencyConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.github.geekuniversity_java_215.cmsbackend.utils.Utils.fieldSetter;
import static com.pivovarit.function.ThrowingRunnable.unchecked;

@Service
@Transactional
@Slf4j
public class AccountService extends BaseRepoAccessService<Account> {

    private final AccountRepository accountRepository;
    private final CurrencyConverterService currencyConverterService;

    @Autowired
    public AccountService(AccountRepository accountRepository, CurrencyConverterService currencyConverterService) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.currencyConverterService = currencyConverterService;
    }

    /**
     * Зачислить на счет
     * @param account
     * @param amount
     * @param currencyCode
     */
    @LogExecutionTime
    public BigDecimal addBalance(Account account, BigDecimal amount, CurrencyCode currencyCode) {

        amount = currencyConverterService.convertCurrency(amount, currencyCode);

        //ToDo: Убрать тестирование
        log.info("Хотим пополнить баланс, id={} balance: {} ", account.getId(), account.getBalance());

        accountRepository.lockByAccount(account);
        log.info("Заблокировали строку, id={} balance: {}", account.getId(), account.getBalance());

        // reload
        account = findById(account.getId()).orElse(null);
        Assert.notNull(account,"Account after reload == null");
        log.info("Перечитали account из базы, id={} balance: {}", account.getId(), account.getBalance());

        // TESTING
        log.info("Усиленно работаем ...");
        unchecked(() -> TimeUnit.MILLISECONDS.sleep(500)).run();

        fieldSetter("balance", account, account.getBalance().add(amount));
        //propertySetter("setBalance", account, BigDecimal.class, account.getBalance().add(amount));
        log.info("Пополняем баланс, id={} balance: {}", account.getId(), account.getBalance());

        accountRepository.save(account);

        log.info("Снимаем блокировку строки, id={} balance: {}", account.getId(), account.getBalance());

        return amount;
    }

    /**
     * Снять со счета
     * @param account
     * @param amount
     * @param currencyCode
     */

    public BigDecimal removeBalance(Account account, BigDecimal amount, CurrencyCode currencyCode) {

        amount = currencyConverterService.convertCurrency(amount, currencyCode);

        accountRepository.lockByAccount(account);

        // reload
        account = findById(account.getId()).orElse(null);
        Assert.notNull(account,"Account after reload == null");

        if(account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0) {

            fieldSetter("balance", account, account.getBalance().subtract(amount));
            accountRepository.save(account);
        }
        else {
            throw new RuntimeException("Не хватает минералов, Милорд");
        }

        return amount;
    }


    public Optional<Account> findByUser(User user) {
        return accountRepository.findByUser(user);
    }

}