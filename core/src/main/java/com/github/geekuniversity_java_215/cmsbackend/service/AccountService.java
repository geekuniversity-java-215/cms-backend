package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> findAllById(List<Long> listId) {

        return accountRepository.findAllById(listId);
    }

    public List<Account> findAll(Specification<Account> spec) {
        return accountRepository.findAll(spec);
    }

    public Account save(Account account) {

        return accountRepository.save(account);
    }


    public void delete(Account account) {

        accountRepository.delete(account);
    }

    public Page<Account> findAll(Specification<Account> spec, PageRequest pageable) {

        return accountRepository.findAll(spec, pageable);
    }
}
