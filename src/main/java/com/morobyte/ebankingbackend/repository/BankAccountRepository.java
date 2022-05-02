package com.morobyte.ebankingbackend.repository;

import com.morobyte.ebankingbackend.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
