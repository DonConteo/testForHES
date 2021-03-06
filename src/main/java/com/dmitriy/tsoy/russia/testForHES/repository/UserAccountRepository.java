package com.dmitriy.tsoy.russia.testForHES.repository;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_account SET username = :username, password = :password, firstname = :firstname, lastname = :lastname WHERE id = :id", nativeQuery = true)
    void updateUserAccount(@Param("id") long id,
                    @Param("username") String username,
                    @Param("password") String password,
                    @Param("firstname") String firstname,
                    @Param("lastname") String lastname);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_account SET username = :username, firstname = :firstname, lastname = :lastname WHERE id = :id", nativeQuery = true)
    void updateUserAccountWithoutPassword(@Param("id") long id,
                           @Param("username") String username,
                           @Param("firstname") String firstname,
                           @Param("lastname") String lastname);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_account SET status = :status WHERE id = :id", nativeQuery = true)
    void changeUserActivity(@Param("id") long id, boolean status);

    @Transactional
    @Query(value = "SELECT * FROM user_account", nativeQuery = true)
    Page<UserAccount> findAllUsers(Pageable pageable);

}