package com.cdtn.kltn.repository.accountlever;

import com.cdtn.kltn.entity.AccountsLever;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsLeverRepository extends JpaRepository<AccountsLever, Long> {
    Optional<AccountsLever> findByCodeClient(String codeClient);
}
