package com.ecore.roles.repository;

import com.ecore.roles.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    Optional<Membership> findByUserIdAndTeamId(UUID userId, UUID teamId);

    @Query("SELECT o from MemberShip o" +
            "WHERE (:teamMemberId IS NULL or  e.id = :teamMemberId)" +
            "AND (:teamId IS NULL or e.teamId = :teamId)")
    Optional<Membership> findByUserIdAndTeamIdFilter(UUID userId, UUID teamId);

    List<Membership> findByRoleId(UUID roleId);
}
