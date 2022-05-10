package com.morobyte.ebankingbackend.service;

import com.morobyte.ebankingbackend.entity.BankAccount;
import com.morobyte.ebankingbackend.entity.CurrentAccount;
import com.morobyte.ebankingbackend.entity.SavingAccount;
import com.morobyte.ebankingbackend.exception.BalanceNotSufficientException;
import com.morobyte.ebankingbackend.exception.BankAccountNotFoundException;
import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;

import java.util.List;

public interface IBankAccountService {
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws ResourceNotFoundException;

    List<CurrentAccount> getCurrentAccounts();

    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws ResourceNotFoundException;

    List<SavingAccount> getSavingAccounts();

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
