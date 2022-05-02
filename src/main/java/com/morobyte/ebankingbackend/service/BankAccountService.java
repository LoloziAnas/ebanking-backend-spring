package com.morobyte.ebankingbackend.service;

import com.morobyte.ebankingbackend.entity.SavingAccount;
import com.morobyte.ebankingbackend.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<SavingAccount> getSavingAccounts() {
        List<SavingAccount> savingAccounts = new ArrayList<>();
        bankAccountRepository.findAll().forEach(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                savingAccounts.add((SavingAccount) bankAccount);
            }
        });
        return savingAccounts;
    }
}
