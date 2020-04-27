package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.aop.LogExecutionTime;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.AccountRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.github.geekuniversity_java_215.cmsbackend.utils.Utils.fieldSetter;
import static com.pivovarit.function.ThrowingRunnable.unchecked;

@Service
@Transactional
@Slf4j
public class AccountService extends BaseRepoAccessService<Account> {
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
    @LogExecutionTime
    public void addBalance(Account account, BigDecimal amount) {

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
        unchecked(() -> TimeUnit.SECONDS.sleep(2));

        fieldSetter("balance", account, account.getBalance().add(amount));
        //propertySetter("setBalance", account, BigDecimal.class, account.getBalance().add(amount));
        log.info("Пополняем баланс, id={} balance: {}", account.getId(), account.getBalance());

        accountRepository.save(account);

        log.info("Снимаем блокировку строки, id={} balance: {}", account.getId(), account.getBalance());
    }

    /**
     * Снять со счета
     * @param account
     * @param amount
     */
    public void removeBalance(Account account, BigDecimal amount) {

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
    }

}
