package com.ecore.roles.service.impl;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.RolesService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    private final RoleRepository roleRepository;
    private final MembershipRepository membershipRepository;

    @Override
    public Role createRole(@NonNull Role r) {
        if (roleRepository.findByName(r.getName()).isPresent()) {
            log.error("[ROLE=SERVICE - CREATE ROLE] - Error - existing role");
            throw new ResourceExistsException(Role.class);
        }
        log.info("[ROLE=SERVICE - CREATE ROLE] - Save new role");
        return roleRepository.save(r);
    }

    @Override
    public Role getRole(@NonNull UUID rid) {
        return roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, rid));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesByFilters(UUID teamMemberId, UUID teamId) {
        // Honestly this part of the endpoint was very confusing, because a teamMember of the Membership entity
        // can only have one role, so a list would never be returned, because it is a OneToOne relationship,
        // this was not very well defined in addition to the nomenclature of the teamMemberId that could have been memberShipId
        Membership membership = membershipRepository.findByUserIdAndTeamIdFilter(teamMemberId, teamId)
                .orElseThrow(() -> new ResourceNotFoundException(Membership.class, teamMemberId));
        return Collections.singletonList(membership.getRole());
    }

}
