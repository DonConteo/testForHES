package com.dmitriy.tsoy.russia.testForHES.repository;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Transactional
    @Query(value = "SELECT name FROM role as r JOIN user_account_roles ur ON ur.roles_id=r.id JOIN user_account as u ON u.id=ur.user_account_id WHERE u.id = :user_id", nativeQuery = true)
    Set<String> getRolesForUser(@Param("user_id") long id);
}
