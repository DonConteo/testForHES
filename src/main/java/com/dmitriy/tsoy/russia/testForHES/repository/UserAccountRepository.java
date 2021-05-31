package com.dmitriy.tsoy.russia.testForHES.repository;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);
}
