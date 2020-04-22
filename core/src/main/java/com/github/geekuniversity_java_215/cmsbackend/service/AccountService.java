package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.aop.LogExecutionTime;
import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.repository.AccountRepository;
import com.github.geekuniversity_java_215.cmsbackend.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.github.geekuniversity_java_215.cmsbackend.utils.Utils.fieldSetter;

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
    @LogExecutionTime
    public void addBalance(Account account, BigDecimal amount) throws InterruptedException {

        //ToDo: Убрать тестирование
        log.info("Хотим пополнить баланс, id={} balance: {} ", account.getId(), account.getBalance());

        accountRepository.lockByAccount(account);
        log.info("Заблокировали строку, id={} balance: {}", account.getId(), account.getBalance());

        // reload
        account = findById(account.getId()).get();
        log.info("Перечитали account из базы, id={} balance: {}", account.getId(), account.getBalance());


        // TESTING
        log.info("Усиленно работаем ...");
        TimeUnit.SECONDS.sleep(5);

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
        account = findById(account.getId()).get();

        if(account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0) {

            fieldSetter("balance", account, account.getBalance().subtract(amount));
            accountRepository.save(account);
        }
        else {
            throw new RuntimeException("Нужно больше минералов, Милорд");
        }
    }

}
