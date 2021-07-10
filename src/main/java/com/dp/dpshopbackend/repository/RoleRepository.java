package com.dp.dpshopbackend.repository;

import com.dp.dpshopbackend.enumeration.RoleName;
import com.dp.dpshopbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
