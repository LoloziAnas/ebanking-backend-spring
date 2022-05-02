package com.morobyte.ebankingbackend.repository;

import com.morobyte.ebankingbackend.entity.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
