package com.cdtn.kltn.repository.user;

import com.cdtn.kltn.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select new User(u.id, u.firstName, u.lastName, u.password, u.role, u.statusAccount, c.codeClient, c.fullName, c.phone, u.rawPassword) " +
            "from User u join Client c on c.userId = u.id " +
            "where ?1 = '' or ?1 is null or (c.fullName like concat('%', ?1, '%')) or (c.phone like concat('%', ?1, '%'))")
    Page<User> findAllUser(Pageable pageable, String inputName);
}
