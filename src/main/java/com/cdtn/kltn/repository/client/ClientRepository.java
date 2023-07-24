package com.cdtn.kltn.repository.client;

import com.cdtn.kltn.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUserId(Long id);

    Optional<Client> findByCodeClient(String codeClient);

    @Query(value = "SELECT c.* FROM client c JOIN users u ON c.user_id = u.id WHERE u.role = 'ROLE_ADMIN'", nativeQuery = true)
    Client findAdmin();

}
