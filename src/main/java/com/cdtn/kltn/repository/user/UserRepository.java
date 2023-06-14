package com.cdtn.kltn.repository.user;

import com.cdtn.kltn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);


    @Query(value = "SELECT u.role FROM users u WHERE u.user_name = :userName", nativeQuery = true)
    String findUserRoleByUsername(@Param("userName") String userName);

    @Query(value = "select u.* from users u join client c on u.id = c.user_id " +
            "where c.code_client = :codeClient", nativeQuery = true)
    User findUserByCodeClient(@Param("codeClient") String codeClient);
}
