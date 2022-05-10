package com.morobyte.ebankingbackend.service;

import com.morobyte.ebankingbackend.entity.*;
import com.morobyte.ebankingbackend.enums.AccountStatus;
import com.morobyte.ebankingbackend.enums.OperationType;
import com.morobyte.ebankingbackend.exception.BalanceNotSufficientException;
import com.morobyte.ebankingbackend.exception.BankAccountNotFoundException;
import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;
import com.morobyte.ebankingbackend.repository.AccountOperationRepository;
import com.morobyte.ebankingbackend.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BankAccountService implements IBankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final ICustomerService customerService;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository, ICustomerService customerService) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.customerService = customerService;
    }


    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws ResourceNotFoundException {
        Customer customer = customerService.getCustomer(customerId);
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);


        return currentAccount;
    }

    @Override
    public List<CurrentAccount> getCurrentAccounts() {
        List<CurrentAccount> currentAccounts = new ArrayList<>();
        bankAccountRepository.findAll().forEach(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                currentAccounts.add((CurrentAccount) bankAccount);
            }
        });
        return currentAccounts;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws ResourceNotFoundException {
        Customer customer = customerService.getCustomer(customerId);
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);

        return savingAccount;
    }

    @Override
    public List<SavingAccount> getSavingAccounts() {
        List<SavingAccount> savingAccounts = new ArrayList<>();
        bankAccountRepository.findAll().forEach(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                savingAccounts.add((SavingAccount) bankAccount);
            }
        });
        return savingAccounts;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

}
