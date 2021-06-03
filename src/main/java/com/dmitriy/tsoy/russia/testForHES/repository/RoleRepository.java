package com.dmitriy.tsoy.russia.testForHES.repository;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
