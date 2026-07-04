package com.digital.wallet.account_service.repository;

import com.digital.wallet.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountIdAndUserId(String id, String userId);
}
